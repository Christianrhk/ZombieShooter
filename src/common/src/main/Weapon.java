package common.src.main;

import common.src.main.Player.WeaponInHand;

public class Weapon extends item {

	private int DAMAGE, ATTACK_SPEED, RANGE, RATE_OF_FIRE;
	WeaponInHand WEAPON_TYPE;
	
	public Weapon(int id, WeaponInHand name, int damage, int attackspeed, int range, int rateOfFire, int cost) {
		
		//Use switch statement on WeaponInHand to set different values depending on enum-value
		
		this.DAMAGE = damage;
		this.NAME = name.toString();
		this.ATTACK_SPEED = attackspeed;
		this.RANGE = range;
		this.RATE_OF_FIRE = rateOfFire;
		this.COST = cost;
		this.ID = id;
		this.ITEM_TYPE = ItemType.Weapon;
		this.WEAPON_TYPE = WeaponInHand.SPACE_GUN;
	}
	
	public int getDamage() {
		return DAMAGE; 
	}
	
	public int getAttackSpeed() {
		return ATTACK_SPEED; 
	}
	
	public int getRange() {
		return RANGE;
	}
	
	public int getRateOfFire() {
		return RATE_OF_FIRE; 
	}
	
}
