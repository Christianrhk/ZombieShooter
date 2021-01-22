package common.src.main;

import common.src.main.Weapon.WeaponInHand;
import common.src.main.Item.ItemType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import org.jspace.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ContentShop extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private boolean bHasBeenPressed;
	static Space channelShopPlayer = new SequentialSpace();
	static Space channelPlayerShop = new SequentialSpace();
	boolean shopVisible;
	static Item[] items = new Item[9];

	public ContentShop(Player p, ContentOverlayHUD HUD) {
		super.setDoubleBuffered(true);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		// Create border:
		Border windowsBorder = BorderFactory.createLineBorder(Color.black, 10);
		super.setBorder(BorderFactory.createTitledBorder(windowsBorder, "Shop", TitledBorder.CENTER, TitledBorder.TOP));

		// Setups up the content of the shop
		setupShop(p, HUD);

	}

	private void setupShop(Player p, ContentOverlayHUD HUD) {

		// Sets up all the itemPanels needed
		super.setLayout(new GridLayout(3, 3, 3, 3));

		Weapon PistolWeapon = StandardWeapons.getPistol();
		items[0] = PistolWeapon;
		JPanel itemPanel0 = createItemPanel(PistolWeapon, p, HUD);
		super.add(itemPanel0, new Integer(0));

		Weapon AssaultWeapon = StandardWeapons.getAssaultrifle();
		items[1] = AssaultWeapon;
		JPanel itemPanel1 = createItemPanel(AssaultWeapon, p, HUD);
		super.add(itemPanel1, new Integer(1));

		Weapon SMGWeapon = StandardWeapons.getSmg();
		items[2] = SMGWeapon;
		JPanel itemPanel2 = createItemPanel(SMGWeapon, p, HUD);
		super.add(itemPanel2, new Integer(2));

		Weapon ShotgunWeapon = StandardWeapons.getShotgun();
		items[3] = ShotgunWeapon;
		JPanel itemPanel3 = createItemPanel(ShotgunWeapon, p, HUD);
		super.add(itemPanel3, new Integer(3));

		Weapon SniperWeapon = StandardWeapons.getSniper();
		items[4] = SniperWeapon;
		JPanel itemPanel4 = createItemPanel(SniperWeapon, p, HUD);
		super.add(itemPanel4, new Integer(4));

		Weapon SpaceWeapon = StandardWeapons.getSpacegun();
		items[5] = SpaceWeapon;
		JPanel itemPanel5 = createItemPanel(SpaceWeapon, p, HUD);
		super.add(itemPanel5, new Integer(5));

		Item armor = new Item(ItemType.Armor, 50, 10);
		items[6] = armor;
		JPanel itemPanel6 = createItemPanel(armor, p, HUD);
		super.add(itemPanel6, new Integer(6));

		Item potion = new Item(ItemType.Potion, 40, 8);
		items[7] = potion;
		JPanel itemPanel7 = createItemPanel(potion, p, HUD);
		super.add(itemPanel7, new Integer(7));

		Item boots = new Item(ItemType.Boots, 50, 50);
		items[8] = boots;
		JPanel itemPanel8 = createItemPanel(boots, p, HUD);
		super.add(itemPanel8, new Integer(8));
	}

	private JPanel createItemPanel(Item itemObject, Player p, ContentOverlayHUD HUD) {
		// Sets up the panel
		JPanel itemPanel = new JPanel();
		itemPanel.setOpaque(true);
		itemPanel.setForeground(Color.black);
		itemPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		itemPanel.setPreferredSize(new Dimension(100, 100));
		itemPanel.setLayout(null);

		// Sets up the name of the item
		JLabel itemName = new JLabel(itemObject.getName());
		itemName.setBounds(10, 8, 150, 30);
		itemName.setFont(new Font("TimesRoman", Font.PLAIN, 22));

		// Sets up the specifications of the item
		JTextArea itemSpecs = new JTextArea();
		itemSpecs.setBounds(10, 40, 164, 100);
		itemSpecs.setFont(new Font("Helvetica", Font.PLAIN, 14));
		itemSpecs.setEditable(false);

		// Objects used for checking the items
		ItemType itemType = itemObject.getType();
		Icon shopIcon = null;

		// Sets up the text for the items based on their item and/or weapon types
		switch (itemObject.getType()) {

		case Weapon:

			Weapon weaponObject = (Weapon) itemObject;
			WeaponInHand weaponType = weaponObject.getWeaponType();
			itemSpecs
					.setText("Damage:\t" + weaponObject.getDamage() + "\nAttackSpeed:\t" + weaponObject.getAttackSpeed()
							+ "\nRange:\t" + weaponObject.getRange() + "\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(196, 196, 196, 255));

			switch (weaponType) {

			case PISTOL:
				shopIcon = setShopImage("pistol");
				break;

			case ASSAULT_RIFLE:
				shopIcon = setShopImage("assaultrifleShop");
				break;

			case SNIPER_RIFLE:
				shopIcon = setShopImage("sniperrifle");
				break;

			case SHOTGUN:
				shopIcon = setShopImage("shotgunShop");
				break;

			case SMG:
				shopIcon = setShopImage("smg");
				break;

			case SPACE_GUN:
				shopIcon = setShopImage("spacegun");
				break;

			default:
				throw new IllegalStateException("Illegal weapon type!\n");
			}

			break;
		case Armor:
			itemSpecs.setText("Armor points:\t" + itemObject.getArmor() + "\n\n\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(250, 252, 140, 255));
			shopIcon = setShopImage("shield");
			break;
		case Potion:
			itemSpecs.setText("Heals:\t" + itemObject.getHealth() + "\n\n\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(231, 136, 84, 255));
			shopIcon = setShopImage("heart");
			break;
		case Boots:
			itemSpecs.setText("Speed:\t+" + itemObject.getSpeed() + "%" + "\n\n\n\nCost:\t" + itemObject.getCost());
			itemPanel.setBackground(new Color(167, 131, 112, 255));
			shopIcon = setShopImage("boots");
			break;
		default:
			throw new IllegalStateException("Illegal item type!\n");
		}

		// Sets up the icon for the item in the shop
		JLabel iconLabel = new JLabel(shopIcon);
		iconLabel.setBounds(115, 145, 59, 32);

		// Sets up the buy-button for the item
		JButton buyButton = new JButton("Buy item");
		buyButton.setBounds(10, 145, 100, 32);
		buyButton.setToolTipText("Buy this item");

		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					// Checks to see if the player even buy the item. If not, the shop is not
					// contacted with a buy command.
					switch (itemType) {

					case Weapon:
						Weapon weaponObject = (Weapon) itemObject;
						WeaponInHand weaponType = weaponObject.getWeaponType();

						weaponObject = (Weapon) itemObject;
						weaponType = weaponObject.getWeaponType();

						if (p.getWIH().equals(weaponType)) {
							System.out.println("You already have this weapon equipped!\n");
							resetShop(itemPanel, buyButton, itemName, itemSpecs, iconLabel);
							return;

						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
						}
						break;
						
					case Armor:
						int currentArmor = p.getArmor();

						if (currentArmor == 200) {
							System.out.print("You already have maximum armor!\n");
							resetShop(itemPanel, buyButton, itemName, itemSpecs, iconLabel);
							return;
							
						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
						}
						break;

					case Potion:
						int currentHealth = p.getHP();

						if (currentHealth == 100) {
							System.out.print("You already have maximum health!\n");
							resetShop(itemPanel, buyButton, itemName, itemSpecs, iconLabel);
							return;
						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
						}
						break;

					case Boots:
						boolean currentBoots = p.getBoots();

						if (currentBoots == true) {
							System.out.print("You already have boots equipped!\n");
							resetShop(itemPanel, buyButton, itemName, itemSpecs, iconLabel);
							return;

						} else {
							channelPlayerShop.put("Buy", itemObject.getID());
						}
						break;

					default:
						throw new IllegalStateException("Illegal item type!\n");
					}

					// Gets signal from the shop, informing the player of the branch taken
					Object[] responseBuyOrQuit = channelShopPlayer.get(new FormalField(String.class));

					Object[] responseBuy = channelShopPlayer.get(new FormalField(String.class),
							new FormalField(Integer.class));

					if (responseBuy[0].equals("ItemBought")) {
						Object[] moneyBack = channelShopPlayer.get(new FormalField(String.class),
								new FormalField(Integer.class));
						
						//Updates the players money
						int currentMoney = (int) moneyBack[1];
						p.setMoney(currentMoney);
						HUD.updateMoney(p);

						// Gives the item
						switch (itemType) {
						case Weapon:
							Weapon weaponObject = (Weapon) itemObject;

							p.setWeapon(weaponObject);
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
							HUD.updateHP(p);
							break;

						case Boots:
							p.setBoots(true);
							p.setMovementSpeed(3);
							HUD.addBootsHUD(p);
							break;

						default:
							throw new IllegalStateException("Illegal item type!\n");
						}

					} else {
						System.out.println("Player has not enough money!\n");

					}

				} catch (InterruptedException e1) {
					System.out.println("Button did not send command to shop!");
					e1.printStackTrace();
				}

				resetShop(itemPanel, buyButton, itemName, itemSpecs, iconLabel);
			}
		});
		
		//Adds the components to the JPanel object
		itemPanel.add(buyButton);
		itemPanel.add(itemName);
		itemPanel.add(itemSpecs);
		itemPanel.add(iconLabel);

		return itemPanel;
	}
	
	// Function to set the shop image of an item
	private Icon setShopImage(String path) {
		try {
			Image img = ImageIO.read(new File("src/images/" + path + ".png"));
			Icon icon = new ImageIcon(img);
			return icon;
		} catch (IOException e) {
			System.out.println(path + ": Icon could not be located!\n");
			e.printStackTrace();
		}
		return null;
	}

	// Resets the shop graphics to avoid the player getting stuck
	// The reason for removing/adding the same components is because the player got
	// stuck when pressing a buy button.
	// By re-adding the components, the problem is somehow fixed
	public void resetShop(JPanel itemPanel, JButton buyButton, JLabel itemName, JTextArea itemSpecs, JLabel iconLabel) {
		itemPanel.removeAll();
		itemPanel.add(buyButton);
		itemPanel.add(itemName);
		itemPanel.add(itemSpecs);
		itemPanel.add(iconLabel);
	}

	public static Space getChannelSP() {
		return channelShopPlayer;
	}

	public static Space getChannelPS() {
		return channelPlayerShop;
	}
	
	static void transactionState(boolean state, Player p) {
		// Player opens the shop (Presses B)
		if (state == true) {
			int currentMoney = p.getMoney();
			try {
				// Starts the shop thread
				new Thread(new setupTransactionLogic(channelShopPlayer, channelPlayerShop, items)).start();

				//2-way opening handshake
				channelPlayerShop.put("IsShopOpen?");
				Object[] handshakeResponse = channelShopPlayer.get(new ActualField("ShopIsOpen"));
				
				// Sends the shop the money the player currently has
				channelPlayerShop.put(currentMoney);
				
				//Gets ACK
				Object[] currentMoneyACK = channelShopPlayer.get(new ActualField("currentMoneyReceived!"));
				
				
			} catch (InterruptedException e) {
				System.out.println("Player could NOT connect to the shop!\n");
				e.printStackTrace();
			}
		}
		// Player closes the shop (Presses B again)
		else {
			try {
				// Initiates termination of the shop thread
				channelPlayerShop.put("CloseShop", -1);
				channelShopPlayer.get(new ActualField("ConnectionTerminated"));
			} catch (InterruptedException e) {
				System.out.println("Player could NOT terminate the connection with the shop!\n");
				e.printStackTrace();
			}
		}
	}
}

//Shop thread
class setupTransactionLogic implements Runnable {
	private Space channelSP, channelPS;
	private int currentMoney;
	private Item[] items;

	public setupTransactionLogic(Space spaceSP, Space spacePS, Item[] items) {
		this.channelSP = spaceSP;
		this.channelPS = spacePS;
		this.currentMoney = 0;
		this.items = items;
	}

	public void run() {
		try {
			while (true) {
				
				//2-way opening handshake
				Object[] handshakeMessage = channelPS.get(new ActualField("IsShopOpen?"));			
				channelSP.put("ShopIsOpen");
				
				// Gets the current money that the player is carrying
				Object[] objectMoney = channelPS.get(new FormalField(Integer.class));
				currentMoney = (int) objectMoney[0];
				
				//Send ACK back to player
				channelSP.put("currentMoneyReceived!");

				// Get command from the player
				Object[] command = channelPS.get(new FormalField(String.class), new FormalField(Integer.class));
				String stringCommand = command[0].toString();
				int itemID = (int) command[1];

				// Enters while-loop if the player wants to buy an item
				while (stringCommand.compareTo("CloseShop") != 0 && itemID != -1) {

					// Inform player of loop taken:
					channelSP.put("PlayerWannaBuy");

					// Finds the price of the item
					int itemCost = items[itemID].getCost();

					//Calculates the money left after the transaction
					int moneyLeft = currentMoney - itemCost;

					// Checks if the player can afford the item
					if (moneyLeft > -1) {
						// The player can afford the item
						channelSP.put("ItemBought", itemID);
						channelSP.put("CurrentMoney", moneyLeft);
						currentMoney = moneyLeft;

					} else {
						// The player can NOT afford the item
						channelSP.put("NotEnoughMoney", itemID);
					}
					
					//Ready to receive a new command
					command = channelPS.get(new FormalField(String.class), new FormalField(Integer.class));
					stringCommand = command[0].toString();
					itemID = (int) command[1];
				}

				// Inform player that the shop has been closed (by the player himself)
				channelSP.put("ConnectionTerminated");
				break;
			}
		} catch (InterruptedException e) {
			System.out.println("Protocol between shop and player failed!\n");
			e.printStackTrace();
		}
	}
}

