package common.src.main;

import java.awt.Point;

public class Player extends Entity {

	int MONEY;
	boolean BOOTS;
	int bulletDelay;

	Weapon weapon;

	public Player(String name) {
		super();
		this.HEALTH_POINTS = 100;
		this.ARMOR = 50;
		this.DAMAGE = 1;
		this.NAME = name;
		this.POSITION = new Point(0, 0);
		this.MOVEMENT_SPEED = 2;
		this.MONEY = 0;
		this.offset = 20;
		this.weapon = StandardWeapons.getPistol();
		this.BOOTS = false;
		this.bulletDelay = 0;
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

	public boolean getBoots() {
		return this.BOOTS;
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

	public void setArmor(int amount) {
		this.ARMOR = amount;
	}

	public void setHealth(int amount) {
		this.HEALTH_POINTS = amount;
	}

	public void setBoots(boolean b) {
		this.BOOTS = b;
	}
	
	public void setMovementSpeed(int amount) {
		this.MOVEMENT_SPEED = amount;
	}
	
	public Weapon.WeaponInHand getWIH(){
		return this.weapon.getWeaponType();
	}
	
	public void setWeapon(Weapon w) {
		this.weapon = w;
		this.bulletDelay = (int)(1.0 / w.getAttackSpeed() * 50.0);
	}
	
	public int getWeaponRange() {
		return this.weapon.getRange();
	}
	
	public int getDamage() {
		return this.DAMAGE + this.weapon.getDamage();
	}
	
	public double getAttackSpeed() {
		return this.weapon.getAttackSpeed();
	}

}
