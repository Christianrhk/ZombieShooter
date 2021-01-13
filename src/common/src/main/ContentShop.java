//Chris lokal fil

package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import org.jspace.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class ContentShop extends JPanel {

	private boolean bHasBeenPressed;
	static Space channelShopPlayer = new SequentialSpace();
	static Space channelPlayerShop = new SequentialSpace();
	boolean shopVisible;

	public ContentShop(Player p) {
		super.setDoubleBuffered(true);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		bHasBeenPressed = false;

		// Create border:
		Border windowsBorder = BorderFactory.createLineBorder(Color.black, 10);
		super.setBorder(BorderFactory.createTitledBorder(windowsBorder, "Shop", TitledBorder.CENTER, TitledBorder.TOP));

		// Initialize by sending the amount of money the
		// player is currently carrying

		// Setups up the content of the shop
		setupShop();

		//item[] items = new item[10];
		//items[0] = new item("TestItem", "TestType", 100);
		// String playerName = "FaetterGuf"; // Get data from App

	}

	private void setupShop() {

		// Sets up all the itemPanels needed
		int n = 3;
		int numberOfItems = 6;
		super.setLayout(new GridLayout(n, n, 3, 3));

		for (int i = 0; i < numberOfItems; i++) {
			JPanel itemPanel = createItemPanel(i, "Good weapon", "tons", "fast", "69", "Cuba");
			super.add(itemPanel, new Integer(i));
		}
		super.add(createItemPanel(7, "Armor", "20", "", "", ""), new Integer(7));
		super.add(createItemPanel(8, "Health Potion", "40", "3", "", ""), new Integer(8));
		super.add(createItemPanel(9, "Boots", "30", "", "", ""), new Integer(9));
		
		JButton closeButton = new JButton("Close");
		//super.add(closeButton);

	} 

	static void transactionState(boolean state, Player p) {
		// Player opens the shop (Presses B)
		if (state == true) {
			int currentMoney = p.getMoney();
			try {
				new Thread(new setupTransactionLogic(channelShopPlayer, channelPlayerShop)).start();
				System.out.println("The shop-thread has started!\n");
				channelPlayerShop.put(currentMoney);
				System.out.println("Player sent the money to the shop!\n");
				System.out.println("Amount: " + currentMoney);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// Player closes the shop (Presses B again)
		else {
			try {
				channelPlayerShop.put("CloseShop", -1);
				channelShopPlayer.get(new ActualField("ConnectionTerminated"));
				System.out.println("Shop was terminated correctly!\n");
			} catch (InterruptedException e) {
				System.out.println("Player could NOT terminate the connection with the shop!\n");
				e.printStackTrace();
			}

		}

	}

	private JPanel createItemPanel(int itemID, String name, String arg1, String arg2, String arg3, String arg4) {
		// Sets up the panel
		JPanel itemPanel = new JPanel();
		itemPanel.setOpaque(true);
		itemPanel.setBackground(Color.cyan);
		itemPanel.setForeground(Color.black);
		itemPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		itemPanel.setPreferredSize(new Dimension(100, 100));
		itemPanel.setLayout(null);

		// Sets up the name of the item
		JLabel itemName = new JLabel(name);
		itemName.setBounds(10, 10, 150, 25);
		itemName.setFont(new Font("TimesRoman", Font.PLAIN, 18));

		// Sets up the specifications of the item, based on the item type
		JTextArea itemStats = new JTextArea();
		if (name.equals("Armor")) {
			itemStats.setText("Armor points:\t" + arg1);
		} else if (name.equals("Health Potion")) {
			itemStats.setText("Heals:\t" + arg1 + "\nCharges:\t" + arg2);
		} else if (name.equals("Boots")) {
			itemStats.setText("Speed:\t+" + arg1 + "%");
		} else {
			itemStats.setText(
					"Damage:\t" + arg1 + "\nRate of Fire:\t" + arg2 + "\nAmmo:\t" + arg3 + "\nRange:\t" + arg4);
		}
		itemStats.setBounds(10, 40, 164, 100);
		itemStats.setFont(new Font("Helvetica", Font.PLAIN, 18));
		itemStats.setEditable(false);

		// Sets up the buy-button for the item
		JButton buyButton = new JButton("Buy item");
		buyButton.setBounds(10, 145, 100, 32);
		buyButton.setToolTipText("Buy this item");

		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {



				// The reason for removing/adding the same components is because the player got
				// stuck when pressing a buy button.
				// By re-adding the components, the problem is somehow fixed

				
				try {
					
					channelPlayerShop.put("Buy", itemID);
					System.out.println("Button pressed!\n");

					// Gets signal from the shop, informing the player of the branch taken
					Object[] responseBuyOrQuit = channelShopPlayer.get(new FormalField(String.class));

					Object[] responseBuy = channelShopPlayer.get(new FormalField(String.class), new ActualField(itemID));
					
					System.out.println("Both responses received!\n");

			
					if (responseBuy[0].equals("ItemBought")) {
						Object[] moneyBack = channelShopPlayer.get(new FormalField(String.class), new FormalField(Integer.class));
						int currentMoney = (int) moneyBack[1];
						

						// Equip the user with the new item //Use the currentMoney variable to update
						// the players money //Display a message, so that the player knows that he //
						// successfully bought the item
						
						System.out.println("The player received the item!\n");

					} else {

						// Display a message, so that the player know the he did not have enough
						// money // to buy the item System.out.println("test"); }

					}
					
				} catch (InterruptedException e1) {
					System.out.println("Button did not send command to shop!");
					e1.printStackTrace();
				}
				

				itemPanel.removeAll();
				itemPanel.add(buyButton);
				itemPanel.add(itemName);
				itemPanel.add(itemStats);
				itemPanel.add(addIcon());
				System.out.println("Reached the end of the button logic!\n");
			}
		});
		
		itemPanel.add(buyButton);
		itemPanel.add(itemName);
		itemPanel.add(itemStats);
		itemPanel.add(addIcon());

		return itemPanel;
	}

	public JLabel addIcon() {

		try {
			Image img = ImageIO.read(new File("src/images/redbox.png"));

			Icon icon = new ImageIcon(img);
			JLabel iconLabel = new JLabel(icon);
			iconLabel.setBounds(115, 145, 59, 32);
			return iconLabel;
		} catch (IOException e) {
			System.out.println("Icon for item could not be located!\n");
			e.printStackTrace();
		}
		return null;
	}
}

class setupTransactionLogic implements Runnable {
	private Space channelSP, channelPS;
	private int currentMoney;
	private item[] items;
	private int totalItems;
	private String playerID;

	public setupTransactionLogic(Space spaceSP, Space spacePS) {
		this.channelSP = spaceSP;
		this.channelPS = spacePS;
		this.currentMoney = 0;
		// this.items = items;
		// this.totalItems = items.length;
		// this.playerID = playerID;
	}

	public void run() {
		try {
			while (true) {

				System.out.print("Shop was started!\n");
				// Gets the current money that the player is carrying
				Object[] objectMoney = channelPS.get(new FormalField(Integer.class));
				currentMoney = (int) objectMoney[0];
				System.out.println(currentMoney + " money was received from the player, awaiting commands!\n");

				// The player is expected to already have the data from the start of the game,
				// as to reduce the amount of data transferred from the host
				// to the player. Instead, the host sends commands to the player, that unlocks
				// new items to buy in the shop. This is done between each round
				// when the shop can be opened.

				// Get command from the player
				Object[] command = channelPS.get(new FormalField(String.class), new FormalField(Integer.class));
				String stringCommand = command[0].toString();
				int itemID = (int) command[1];
				System.out.print("Shop received command: "+stringCommand+"\n");

				// Enters while-loop if the player wants to buy an item
				while (stringCommand.compareTo("CloseShop") != 0 && itemID != -1) {

					// Inform player of loop taken:
					channelSP.put("PlayerWannaBuy");

					// Finds the price of the item
					// int itemCost = items[itemID].getCost();
					int itemCost = 3;

					int moneyLeft = currentMoney - itemCost;

					// Checks if the player can afford the item
					if (moneyLeft > -1) {
						// The player can afford the item
						channelSP.put("ItemBought", itemID);
						channelSP.put("CurrentMoney", moneyLeft);
						currentMoney = moneyLeft;
						System.out.println("Player now has " + moneyLeft + " money left!\n");
						// <Signal this to the graphics/stat component>

					} else {
						// The player can NOT afford the item
						channelSP.put("NotEnoughMoney", itemID);
						System.out.println("Player had not enough money! " + moneyLeft + "\n");
					}

					// Gets next command from the player
					System.out.println("Awaiting new commands from the player!\n");
					//channelPS.put("CloseShop",-1);
					command = channelPS.get(new FormalField(String.class), new FormalField(Integer.class));
					stringCommand=command[0].toString();
					System.out.println("Read a new command from the player: "+command[0].toString()+"\n");
					//break;
					
				}

				// Inform player that the shop has been closed (by the player himself)

				channelSP.put("ConnectionTerminated");
				System.out.println("Shop terminated!\n");
				break;
			}
		} catch (InterruptedException e) {
			System.out.println("Protocol between shop and player failed!\n");
			e.printStackTrace();
		}
	}
}
