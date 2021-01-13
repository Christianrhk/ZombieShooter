package common.src.main;

import java.awt.Point;

public class Player extends Entity {

	int MONEY;
	
	enum WeaponInHand {SPACE_GUN}; // ADD POSSIBLE WEAPONS HERE.
	WeaponInHand weapon;

	public Player(String name) {
		super();
		this.HEALTH_POINTS = 100;
		this.ARMOR = 50;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.NAME = name;
		this.POSITION = new Point(0, 0);
		this.MOVEMENT_SPEED = 2;
		this.MONEY = 0;
		this.offset = 32;
		this.weapon = WeaponInHand.SPACE_GUN;
		/*
		try {
			this.IMAGE = ImageIO.read(new File("src/images/clown.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public int getX() {
		return this.POSITION.x;
	}

	public int getY() {
		return this.POSITION.y;
	}

	public int getHP() {
		return this.HEALTH_POINTS;
	}
	
	public int getArmor() {
		return this.ARMOR;
	}
	
	public int getMoney() {
		return this.MONEY;
	}
	
	public void giveMoney(int amount) {
		this.MONEY += amount;
	}
	
	public void subtractMoney(int amount) {
		this.MONEY -= amount;
	}
	
	public void setMoney(int amount) {
		this.MONEY = amount;
	}

}
