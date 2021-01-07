package common.src.main;

import java.io.IOException;
import java.net.UnknownHostException;
import org.jspace.*;

public class Shop {

	public static void shopLogic() {

		// Space
		Space channel = new SequentialSpace();

		// Items in storage
		item[] items = new item[10];
		items[0] = new item(0, "TestItem", "TestType", 100);
		// <Add more and correct items to storage>;

		// Logic for buying

	}
}

class moneyUpdating implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}

class processShop implements Runnable {
	private int currentMoney;
	private Space channel;
	private item[] items;
	private int totalItems;

	public processShop(Space space, item[] items) {
		this.channel = space;
		this.items = items;
		this.totalItems = items.length;
	}

	@Override
	public void run() {
		try {
			channel.get(new ActualField("AccessShop"));
			Object[] objectMoney = channel.get(new ActualField("CurrentMoney"), new FormalField(Integer.class));

			// Gets the new money that the player has generated
			currentMoney = (int) objectMoney[1];

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

				// Finds the price of the item
				int itemCost = items[itemID].getCost();

				// Checks if the player can afford the item
				if (currentMoney - itemCost > -1) {
					// The player can afford the item
					channel.put("ItemBought", itemID);
				} else {
					// The player can NOT afford the item
					channel.put("NotEnoughMoney", itemID);
				}

				// Gets next command from the player
				command = channel.get(new FormalField(String.class), new ActualField(Integer.class));
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
