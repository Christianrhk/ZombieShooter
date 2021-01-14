package common.src.main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class item {
	String NAME;
	Icon ICON;
	// <Add more variables depending on item type >
	static final AtomicInteger count = new AtomicInteger(-1);
	int COST, ID, ARMOR, HEALTH, CHARGES, SPEED;

	enum ItemType {
		Weapon, Armor, Potion, Boots
	}

	ItemType ITEM_TYPE;

	// Default constructor
	public item() {
		this.ID = count.incrementAndGet();
	}

	// Constructor for armor and boots
	public item(ItemType itemType, int arg, int cost) {
		this.ID = count.incrementAndGet();
		this.ITEM_TYPE = itemType;
		this.COST = cost;

		switch (ITEM_TYPE) {

		case Armor:
			this.ARMOR = arg;
			this.NAME = "Armor";

			try {
				Image img = ImageIO.read(new File("src/images/shield.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the armor item could not be located!\n");
				e.printStackTrace();
			}
			break;
		case Boots:
			this.SPEED = arg;
			this.NAME = "Boots";

			try {
				Image img = ImageIO.read(new File("src/images/redbox.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the boots item could not be located!\n");
				e.printStackTrace();
			}

			break;
		default:
			throw new IllegalStateException("Illegal item type!\n");
		}
	}

	// Constructor for potion
	public item(ItemType itemType, int health, int charges, int cost) {
		this.ID = count.incrementAndGet();
		this.ITEM_TYPE = itemType;

		switch (itemType) {

		case Potion:
			this.HEALTH = health;
			this.CHARGES = charges;
			this.COST = cost;
			this.NAME = "Health Potions";

			try {
				Image img = ImageIO.read(new File("src/images/heart.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the armor item could not be located!\n");
				e.printStackTrace();
			}

			break;
		default:
			throw new IllegalStateException("Illegal item type!\n");
		}
	}

	public int getCost() {
		return COST;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return NAME;
	}

	public ItemType getType() {
		return ITEM_TYPE;
	}

	public int getArmor() {
		return ARMOR;
	}

	public int getHealth() {
		return HEALTH;
	}

	public int getCharges() {
		return CHARGES;
	}

	public int getSpeed() {
		return SPEED;
	}

	public Icon getIcon() {
		return ICON;
	}

}
