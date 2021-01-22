package common.src.main;

import java.util.concurrent.atomic.AtomicInteger;

class Item {
	String NAME;
	static final AtomicInteger count = new AtomicInteger(-1);
	int COST, ID, ARMOR, HEALTH, CHARGES, SPEED;

	enum ItemType {
		Weapon, Armor, Potion, Boots
	}

	ItemType ITEM_TYPE;

	// Default constructor
	public Item() {
		this.ID = count.incrementAndGet();
	}

	// Constructor for armor and boots
	public Item(ItemType itemType, int arg, int cost) {
		this.ID = count.incrementAndGet();
		this.ITEM_TYPE = itemType;
		this.COST = cost;

		switch (ITEM_TYPE) {

		case Armor:
			this.ARMOR = arg;
			this.NAME = "Armor";

			break;
		case Boots:
			this.SPEED = arg;
			this.NAME = "Boots";

			break;
		case Potion:
			this.HEALTH = arg;
			this.NAME = "Restore Health";

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
