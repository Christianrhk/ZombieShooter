package common.src.main;

import java.io.IOException;
import java.net.UnknownHostException;
import org.jspace.*;

public class Shop {

	public static void shopLogic() {

		// Space
		SpaceRepository repository = new SpaceRepository();
		Space channel = new SequentialSpace();

		// Items in storage
		item[] items = new item[10];
		//items[0] = new item("TestItem", "TestType", 100);
		// <Add more and correct items to storage>;

		// Logic for buying

		// Starting threads
		// <Need to know when to start accessPowerthreads for for all players>
		int currentRound = 0; // Get data from App
		boolean startShopping = false;
		boolean threadRunning = false;
		int numberOfPlayers = 0; // Get data from App
		String playerName = "FaetterGuf"; //Get data from App
		
	    Thread thread = new Thread(new processShop(channel, items, playerName));
		
		while (true) {
			if (currentRound == 0 && threadRunning == false) {
				thread.start();
				threadRunning = true;
			}
			if (currentRound != 0) {
				thread.interrupt();
				threadRunning = false;

			}

		}

	}
}

class processShop implements Runnable {
	private int currentMoney;
	private Space channel;
	private item[] items;
	private int totalItems;
	private String playerID;

	public processShop(Space space, item[] items, String playerID) {
		this.channel = space;
		this.items = items;
		this.totalItems = items.length;
		this.playerID = playerID;
	}

	@Override
	public void run() {
		try {
			//Runs in a while-loop, so the player can access the shop multiple times during each break
			while (true) {
				channel.get(new ActualField("AccessShop"));
				Object[] objectMoney = channel.get(new FormalField(Integer.class));

				// Gets the new money that the player has generated
				currentMoney = (int) objectMoney[0];

				// Send command to open gui
				channel.put("OpenShopGui");

				// The player is expected to already have the data from the start of the game,
				// as to reduce the amount of data transferred from the host
				// to the player. Instead, the host sends commands to the player, that unlocks
				// new items to buy in the shop. This is done between each round
				// when the shop can be opened.

				// <Get round number and save it in variable below>>
				int currentRound = -1;
				channel.put("UnlockItems", currentRound);

				Object[] command = channel.get(new FormalField(String.class), new ActualField(Integer.class));
				String stringCommand = command[0].toString();
				int itemID = (int) command[1];

				// Enters while-loop if the player wants to buy an item
				while (stringCommand.compareTo("CloseShop") != 0 && itemID != -1) {

					//Inform player of loop taken:
					channel.put("PlayerWannaBuy");
					
					// Finds the price of the item
					int itemCost = items[itemID].getCost();
					
					int moneyLeft = currentMoney - itemCost;

					// Checks if the player can afford the item
					if (moneyLeft > -1) {
						// The player can afford the item
						channel.put("ItemBought", itemID);
						channel.put("CurrentMoney", moneyLeft);
						currentMoney = moneyLeft;
						//<Signal this to the graphics/stat component>
						
					} else {
						// The player can NOT afford the item
						channel.put("NotEnoughMoney", itemID);
					}

					// Gets next command from the player
					command = channel.get(new FormalField(String.class), new ActualField(Integer.class));
				}
				
				//Inform player that the shop has been closed (by the player himself)
				channel.put("CloseGUI");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
