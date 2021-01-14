//Chris lokal fil

package common.src.main;

import common.src.main.Player.WeaponInHand;
import common.src.main.item.ItemType;

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
	static item[] items = new item[9];

	public ContentShop(Player p, ContentOverlayHUD HUD) {
		super.setDoubleBuffered(true);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		bHasBeenPressed = false;

		// p.setHealth(50); //REMOVE WHEN BUG HAS BEEN FIXED!
		// HUD.updateHP(p);

		// Create border:
		Border windowsBorder = BorderFactory.createLineBorder(Color.black, 10);
		super.setBorder(BorderFactory.createTitledBorder(windowsBorder, "Shop", TitledBorder.CENTER, TitledBorder.TOP));

		// Initialize by sending the amount of money the
		// player is currently carrying

		// Setups up the content of the shop
		setupShop(p, HUD);

		// item[] items = new item[10];
		// items[0] = new item("TestItem", "TestType", 100);
		// String playerName = "FaetterGuf"; // Get data from App

	}

	private void setupShop(Player p, ContentOverlayHUD HUD) {

		// Sets up all the itemPanels needed
		int n = 3;
		int numberOfWeapons = 6;
		super.setLayout(new GridLayout(n, n, 3, 3));

		/*
		 * for (int i = 0; i < numberOfItems; i++) { JPanel itemPanel =
		 * createItemPanel(i, "Good weapon", "tons", "fast", "69", "Cuba", p, HUD);
		 * super.add(itemPanel, new Integer(i)); }
		 */

		for (int i = 0; i < numberOfWeapons; i++) {
			Weapon weapon = new Weapon(i, WeaponInHand.SPACE_GUN, 1, 2, 3, 4, 5);
			items[i] = weapon;
			JPanel itemPanel = createItemPanel(weapon, p, HUD);
			super.add(itemPanel, new Integer(i));
		}

		item armor = new item(ItemType.Armor, 50, 10);
		items[6] = armor;
		JPanel itemPanel = createItemPanel(armor, p, HUD);
		super.add(itemPanel, new Integer(6));

		item potion = new item(ItemType.Potion, 40, 8);
		items[7] = potion;
		itemPanel = createItemPanel(potion, p, HUD);
		super.add(itemPanel, new Integer(7));

		item boots = new item(ItemType.Boots, 50, 50);
		items[8] = boots;
		itemPanel = createItemPanel(boots, p, HUD);
		super.add(itemPanel, new Integer(8));

		// super.add(createItemPanel(7, "Armor", "20", "", "", "", p, HUD), new
		// Integer(7));
		// super.add(createItemPanel(8, "Health Potion", "40", "3", "", "", p, HUD), new
		// Integer(8));
		// super.add(createItemPanel(9, "Boots", "30", "", "", "", p, HUD), new
		// Integer(9));

		// JButton closeButton = new JButton("Close");
		// super.add(closeButton);

	}

	static void transactionState(boolean state, Player p) {
		// Player opens the shop (Presses B)
		if (state == true) {
			int currentMoney = p.getMoney();
			try {
				new Thread(new setupTransactionLogic(channelShopPlayer, channelPlayerShop, items)).start();
				// System.out.println("The shop-thread has started!\n");
				channelPlayerShop.put(currentMoney);
				// System.out.println("Player sent the money to the shop!\n");
				// System.out.println("Amount: " + currentMoney);
			} catch (InterruptedException e) {
				System.out.println("Player could NOT connect to the shop!\n");
				e.printStackTrace();
			}

		}
		// Player closes the shop (Presses B again)
		else {
			try {
				channelPlayerShop.put("CloseShop", -1);
				channelShopPlayer.get(new ActualField("ConnectionTerminated"));
				// System.out.println("Shop was terminated correctly!\n");
			} catch (InterruptedException e) {
				System.out.println("Player could NOT terminate the connection with the shop!\n");
				e.printStackTrace();
			}

		}

	}

	private JPanel createItemPanel(item itemObject, Player p, ContentOverlayHUD HUD) {
		// Sets up the panel
		JPanel itemPanel = new JPanel();
		itemPanel.setOpaque(true);
		itemPanel.setForeground(Color.black);
		itemPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		itemPanel.setPreferredSize(new Dimension(100, 100));
		itemPanel.setLayout(null);

		// Sets up the name of the item
		JLabel itemName = new JLabel(itemObject.getName());
		itemName.setBounds(10, 10, 150, 25);
		itemName.setFont(new Font("TimesRoman", Font.PLAIN, 22));

		// Sets up the specifications of the item
		JTextArea itemSpecs = new JTextArea();
		itemSpecs.setBounds(10, 40, 164, 100);
		itemSpecs.setFont(new Font("Helvetica", Font.PLAIN, 14));
		itemSpecs.setEditable(false);

		switch (itemObject.getType()) {

		case Weapon:
			Weapon weaponObject = (Weapon) itemObject;
			// System.out.print("AttackSpeed: " + weaponObject.getAttackSpeed());
			itemSpecs
					.setText("Damage:\t" + weaponObject.getDamage() + "\nAttackSpeed:\t" + weaponObject.getAttackSpeed()
							+ "\nRange:\t" + weaponObject.getRange() + "\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(196, 196, 196, 255));
			break;
		case Armor:
			itemSpecs.setText("Armor points:\t" + itemObject.getArmor() + "\n\n\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(250, 252, 140, 255));
			break;
		case Potion:
			itemSpecs.setText("Heals:\t" + itemObject.getHealth() + "\n\n\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(231, 136, 84, 255));
			break;
		case Boots:
			itemSpecs.setText("Speed:\t+" + itemObject.getSpeed() + "%" + "\n\n\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(167, 131, 112, 255));
			break;
		default:
			throw new IllegalStateException("Illegal item type!\n");
		}

		// Sets up the icon for the item
		JLabel iconLabel = new JLabel(itemObject.getIcon());
		iconLabel.setBounds(115, 145, 59, 32);

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

					// Tests if the player can buy this item locally
					ItemType itemType = itemObject.getType();

					switch (itemType) {

					case Weapon:
						// To-do
						channelPlayerShop.put("Buy", itemObject.getID());
						// System.out.println("Player wants to buy item: " + itemObject.getID() + "\n");
						break;
					case Armor:
						int currentArmor = p.getArmor();

						if (currentArmor == 200) {
							System.out.print("You already have maximum armor!\n");
							itemPanel.removeAll();
							itemPanel.add(buyButton);
							itemPanel.add(itemName);
							itemPanel.add(itemSpecs);
							itemPanel.add(iconLabel);
							return;
						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
							// System.out.println("Player wants to buy item: " + itemObject.getID() + "\n");
						}
						break;

					case Potion:
						int currentHealth = p.getHP();

						if (currentHealth == 100) {
							System.out.print("You already have maximum health!\n");
							itemPanel.removeAll();
							itemPanel.add(buyButton);
							itemPanel.add(itemName);
							itemPanel.add(itemSpecs);
							itemPanel.add(iconLabel);
							return;
						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
							// System.out.println("Player wants to buy item: " + itemObject.getID() + "\n");
						}
						break;

					case Boots:
						boolean currentBoots = p.getBoots();

						if (currentBoots == true) {
							System.out.print("You already have boots equipped!\n");
							itemPanel.removeAll();
							itemPanel.add(buyButton);
							itemPanel.add(itemName);
							itemPanel.add(itemSpecs);
							itemPanel.add(iconLabel);
							return;
						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
							// System.out.println("Player wants to buy item: " + itemObject.getID() + "\n");
						}
						break;

					default:
						channelPlayerShop.put("Buy", itemObject.getID());
						// System.out.println("Player wants to buy item: " + itemObject.getID() + "\n");
						break;

					}

					// channelPlayerShop.put("Buy", itemObject.getID());
					// System.out.println("Player wants to buy item: " + itemObject.getID() + "\n");

					// Gets signal from the shop, informing the player of the branch taken
					Object[] responseBuyOrQuit = channelShopPlayer.get(new FormalField(String.class));

					// System.out.println("Player got response: " +
					// responseBuyOrQuit[0].toString());

					Object[] responseBuy = channelShopPlayer.get(new FormalField(String.class),
							new FormalField(Integer.class));

					// System.out.println("Player got response: " + responseBuy[0].toString() + " "
					// + responseBuy[1].toString());
					// System.out.println("Both responses received!\n");

					if (responseBuy[0].equals("ItemBought")) {
						Object[] moneyBack = channelShopPlayer.get(new FormalField(String.class),
								new FormalField(Integer.class));
						int currentMoney = (int) moneyBack[1];
						p.setMoney(currentMoney);
						HUD.updateMoney(p);

						// System.out.println("The player money was set to: " + currentMoney + "\n");

						// Equip the user with the new item //Use the currentMoney variable to update
						// the players money //Display a message, so that the player knows that he //
						// successfully bought the item

						// System.out.println("The player received the item!\n");

						// Gives the item
						switch (itemType) {

						case Weapon:
							// To-do
							break;
						case Armor:
							int currentArmor = p.getArmor();
							int newArmor = currentArmor + 50;

							if (newArmor > 200) {
								p.setArmor(200);
							} else {
								p.setArmor(newArmor);
							}
							HUD.updateArmor(p);
							break;

						case Potion:
							int currentHealth = p.getHP();
							int newHealth = currentHealth + 40;

							if (newHealth > 100) {
								p.setHealth(100);
							} else {
								p.setHealth(newHealth);
							}
							// System.out.println("New Health: "+newHealth);
							HUD.updateHP(p);
							break;

						case Boots:

							p.setBoots(true);
							p.setMovementSpeed(3);
							HUD.addBootsHUD(p);
							System.out.println("Player now has boots equipped!\n");
							// Update visual of boots on players

							break;

						default:

							break;

						}

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
				itemPanel.add(itemSpecs);
				itemPanel.add(iconLabel);
				// System.out.println("Reached the end of the button logic!\n");
			}
		});

		itemPanel.add(buyButton);
		itemPanel.add(itemName);
		itemPanel.add(itemSpecs);
		itemPanel.add(iconLabel);

		return itemPanel;
	}

	public JTextArea createItemSpecs(item itemObject) {

		// Sets up the specifications of the item, based on the item type
		JTextArea itemSpecs = new JTextArea();
		itemSpecs.setBounds(10, 40, 164, 100);
		itemSpecs.setFont(new Font("Helvetica", Font.PLAIN, 18));
		itemSpecs.setEditable(false);

		switch (itemObject.getType()) {

		case Armor:
			itemSpecs.setText("Armor points:\t" + itemObject.getArmor());
			break;
		case Potion:
			itemSpecs.setText("Heals:\t" + itemObject.getHealth() + "\nCharges:\t" + itemObject.getCharges());
			break;
		case Boots:
			itemSpecs.setText("Speed:\t+" + itemObject.getSpeed() + "%");
			break;
		default:
			throw new IllegalStateException("Illegal item type!\n");
		}
		return itemSpecs;
	}

	public JTextArea createItemSpecs(Weapon weapon) {

		JTextArea itemSpecs = new JTextArea();
		itemSpecs.setText("Damage:\t" + weapon.getDamage() + "\nRate of Fire:\t" + weapon.getRateOfFire()
				+ "\nAttack Speed:\t" + weapon.getAttackSpeed() + "\nRange:\t" + weapon.getRange());

		itemSpecs.setBounds(10, 40, 164, 100);
		itemSpecs.setFont(new Font("Helvetica", Font.PLAIN, 18));
		itemSpecs.setEditable(false);
		return itemSpecs;
	}

	/*
	 * public JLabel addIcon() {
	 * 
	 * try { Image img = ImageIO.read(new File("src/images/redbox.png"));
	 * 
	 * Icon icon = new ImageIcon(img); JLabel iconLabel = new JLabel(icon);
	 * iconLabel.setBounds(115, 145, 59, 32); return iconLabel; } catch (IOException
	 * e) { System.out.println("Icon for item could not be located!\n");
	 * e.printStackTrace(); } return null; }
	 */

	public static Space getChannelSP() {
		return channelShopPlayer;
	}

	public static Space getChannelPS() {
		return channelPlayerShop;
	}
}

class setupTransactionLogic implements Runnable {
	private Space channelSP, channelPS;
	private int currentMoney;
	private item[] items;
	private int totalItems;
	private String playerID;

	public setupTransactionLogic(Space spaceSP, Space spacePS, item[] items) {
		this.channelSP = spaceSP;
		this.channelPS = spacePS;
		this.currentMoney = 0;
		this.items = items;
		// this.totalItems = items.length;
		// this.playerID = playerID;
	}

	public void run() {
		try {
			while (true) {

				// System.out.print("Shop was started!\n");
				// Gets the current money that the player is carrying
				Object[] objectMoney = channelPS.get(new FormalField(Integer.class));
				currentMoney = (int) objectMoney[0];
				// System.out.println(currentMoney + " money was received from the player,
				// awaiting commands!\n");

				// The player is expected to already have the data from the start of the game,
				// as to reduce the amount of data transferred from the host
				// to the player. Instead, the host sends commands to the player, that unlocks
				// new items to buy in the shop. This is done between each round
				// when the shop can be opened.

				// Get command from the player
				Object[] command = channelPS.get(new FormalField(String.class), new FormalField(Integer.class));
				String stringCommand = command[0].toString();
				int itemID = (int) command[1];
				// System.out.print("Shop received command: " + stringCommand + " " + itemID +
				// "\n");

				// Enters while-loop if the player wants to buy an item
				while (stringCommand.compareTo("CloseShop") != 0 && itemID != -1) {

					// Inform player of loop taken:
					channelSP.put("PlayerWannaBuy");

					// Finds the price of the item
					// int itemCost = items[itemID].getCost();
					int itemCost = items[itemID].getCost();

					int moneyLeft = currentMoney - itemCost;

					// Checks if the player can afford the item
					if (moneyLeft > -1) {
						// The player can afford the item
						channelSP.put("ItemBought", itemID);
						channelSP.put("CurrentMoney", moneyLeft);
						currentMoney = moneyLeft;
						// System.out.println("Player now has " + moneyLeft + " money left!\n");

						// <Signal this to the graphics/stat component>

					} else {
						// The player can NOT afford the item
						channelSP.put("NotEnoughMoney", itemID);
						System.out.println("Player had not enough money! " + moneyLeft + "\n");
					}

					// Gets next command from the player
					// System.out.println("Awaiting new commands from the player!\n");
					// channelPS.put("CloseShop",-1);
					command = channelPS.get(new FormalField(String.class), new FormalField(Integer.class));
					stringCommand = command[0].toString();
					itemID = (int) command[1];
					// System.out.println("Read a new command from the player: " +
					// command[0].toString() + " " + itemID + "\n");
					// break;

				}

				// Inform player that the shop has been closed (by the player himself)

				channelSP.put("ConnectionTerminated");
				// System.out.println("Shop terminated!\n");
				break;
			}
		} catch (InterruptedException e) {
			System.out.println("Protocol between shop and player failed!\n");
			e.printStackTrace();
		}
	}
}
