package common.src.main;

import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;

class item {
	String NAME;
	ImageIcon icon;
	// <Add more variables depending on item type >
	static final AtomicInteger count = new AtomicInteger(-1);
	int COST, ID, ARMOR, HEALTH, CHARGES, SPEED;

	enum ItemType {
		Weapon, Armor, Potion, Boots
	}

	ItemType ITEM_TYPE;

	public item() {
		this.ID = count.incrementAndGet();
	}

	public item(ItemType itemType, int arg, int cost) {
		this.ID = count.incrementAndGet();
		this.ITEM_TYPE = itemType;
		this.COST = cost;

		if (itemType.equals(ItemType.Armor)) {
			System.out.println("Hell yes\n");
		}

		switch (ITEM_TYPE) {

		case Armor:
			this.ARMOR = arg;
			this.NAME = "Armor";
			break;
		case Boots:
			this.SPEED = arg;
			this.NAME = "Boots";
			break;
		default:
			System.out.println("Problem: " + ITEM_TYPE.toString());
			throw new IllegalStateException("Illegal item type!\n");
		}
	}

	public item(ItemType itemType, int health, int charges, int cost) {
		this.ID = count.incrementAndGet();
		this.ITEM_TYPE = itemType;

		switch (itemType) {

		case Potion:
			this.HEALTH = health;
			this.CHARGES = charges;
			this.COST = cost;
			this.NAME = "BigAssBong";
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

}
