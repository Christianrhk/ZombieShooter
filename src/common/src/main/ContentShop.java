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
	Space channel = new SequentialSpace();
	boolean shopVisible;

	public ContentShop() {
		super.setDoubleBuffered(true);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		bHasBeenPressed = false;

		// Create border:
		Border windowsBorder = BorderFactory.createLineBorder(Color.black, 10);
		super.setBorder(BorderFactory.createTitledBorder(windowsBorder, "Shop", TitledBorder.CENTER, TitledBorder.TOP));

		// Sets up the channel(tupleSpace) and protocol
		Space channel = new SequentialSpace();
		int currentMoney = 10; // Get from player;
		try {
			channel.put(currentMoney);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Initialize by sending the amount of money the
			// player is currently carrying

		// Setups up the content of the shop
		setupShop();

		item[] items = new item[10];
		items[0] = new item("TestItem", "TestType", 100);
		String playerName = "FaetterGuf"; // Get data from App
		
		// new Thread(new setupTransactionLogic(channel, items, playerName)).start();

	}

	private void setupShop() {

		// Sets up all the itemPanels needed
		int n = 3;
		int numberOfItems = 6;
		super.setLayout(new GridLayout(n, n, 10, 10));

		for (int i = 0; i < numberOfItems; i++) {
			JPanel itemPanel = createItemPanel(i, "Good weapon", "tons", "fast", "69", "Cuba");
			super.add(itemPanel, new Integer(i));
		}
		super.add(createItemPanel(7, "Armor", "20", "", "", ""), new Integer(7));
		super.add(createItemPanel(8, "Health Potion", "40", "3", "", ""), new Integer(8));
		super.add(createItemPanel(9, "Boots", "30", "", "", ""), new Integer(9));

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
			itemStats.setText("Reduction:\t" + arg1 + "%");
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
				System.out.println("Button pressed!");

				// The reason for removing/adding the same components is because the player got
				// stuck when pressing a buy button.
				// By re-adding the components, the problem is somehow fixed
				itemPanel.removeAll();
				itemPanel.add(buyButton);
				itemPanel.add(itemName);
				itemPanel.add(itemStats);
				itemPanel.add(addIcon());

			}
		});
		/*
		 * try {
		 * 
		 * channel.put("Buy", itemID);
		 * 
		 * // Gets signal from the shop, informing the player of the branch taken
		 * Object[] responseBuyOrQuit = channel.get(new FormalField(String.class));
		 * 
		 * Object[] responseBuy = channel.get(new FormalField(String.class), new
		 * ActualField(itemID));
		 * 
		 * if (responseBuy[0].equals("ItemBought")) { Object[] moneyBack =
		 * channel.get(new FormalField(String.class), new FormalField(Integer.class));
		 * int currentMoney = (int) moneyBack[1];
		 * 
		 * // Equip the user with the new item //Use the currentMoney variable to update
		 * // the players money //Display a message, so that the player knows that he //
		 * successfully bought the item
		 * 
		 * } else {
		 * 
		 * // Display a message, so that the player know the he did not have enough
		 * money // to buy the item System.out.println("test"); }
		 * 
		 * } catch (InterruptedException e1) {
		 * System.out.println("Button did not send command to shop!");
		 * e1.printStackTrace(); }
		 * 
		 * } });
		 */
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
	/*
	 * @Override public void keyPressed(KeyEvent e) { int keyCode = e.getKeyCode();
	 * if (keyCode == KeyEvent.VK_B && shopVisible == false) { shopVisible = true;
	 * super.setVisible(true); System.out.println("B-press registered"); } else if
	 * (keyCode == KeyEvent.VK_B && shopVisible) { shopVisible = false;
	 * super.setVisible(false); // sendToBack(super);
	 * System.out.println("B-press registered"); } }
	 * 
	 * @Override public void keyReleased(KeyEvent e) { // TODO Auto-generated method
	 * stub int keyCode = e.getKeyCode(); if (keyCode == KeyEvent.VK_B &&
	 * shopVisible == false) { shopVisible = true; super.setVisible(true);
	 * System.out.println("B-press registered"); } else if (keyCode == KeyEvent.VK_B
	 * && shopVisible) { shopVisible = false; super.setVisible(false); //
	 * sendToBack(super); System.out.println("B-press registered"); }
	 * 
	 * }
	 * 
	 * @Override public void keyTyped(KeyEvent e) { // TODO Auto-generated method
	 * stub int keyCode = e.getKeyCode(); if (keyCode == KeyEvent.VK_B &&
	 * shopVisible == false) { shopVisible = true; super.setVisible(true);
	 * System.out.println("B-press registered"); } else if (keyCode == KeyEvent.VK_B
	 * && shopVisible) { shopVisible = false; super.setVisible(false); //
	 * sendToBack(super); System.out.println("B-press registered"); }
	 * 
	 * }
	 */
}

class setupTransactionLogic implements Runnable {
	private Space channel;
	private int currentMoney;
	private item[] items;
	private int totalItems;
	private String playerID;

	public setupTransactionLogic(Space space, item[] items, String playerID) {
		this.channel = space;
		this.currentMoney = 0;
		this.items = items;
		this.totalItems = items.length;
		this.playerID = playerID;
	}

	public void run() {
		try {
			while (true) {

				// Gets the current money that the player is carrying
				Object[] objectMoney = channel.get(new FormalField(Integer.class));
				currentMoney = (int) objectMoney[0];

				// The player is expected to already have the data from the start of the game,
				// as to reduce the amount of data transferred from the host
				// to the player. Instead, the host sends commands to the player, that unlocks
				// new items to buy in the shop. This is done between each round
				// when the shop can be opened.

				// Get command from the player
				Object[] command = channel.get(new FormalField(String.class), new FormalField(Integer.class));
				String stringCommand = command[0].toString();
				int itemID = (int) command[1];

				// Enters while-loop if the player wants to buy an item
				while (stringCommand.compareTo("CloseShop") != 0 && itemID != -1) {

					// Inform player of loop taken:
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
						// <Signal this to the graphics/stat component>

					} else {
						// The player can NOT afford the item
						channel.put("NotEnoughMoney", itemID);
					}

					// Gets next command from the player
					command = channel.get(new FormalField(String.class), new FormalField(Integer.class));
				}

				// Inform player that the shop has been closed (by the player himself)
				channel.put("CloseGUI");
			}
		} catch (InterruptedException e) {
			System.out.println("Protocol between shop and player failed!\n");
			e.printStackTrace();
		}
	}
}
