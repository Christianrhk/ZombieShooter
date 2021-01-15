package common.src.main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Weapon extends item {
	
	enum WeaponInHand {
		SPACE_GUN,
		SNIPER_RIFLE,
		ASSAULT_RIFLE,
		PISTOL,
		SMG,
		SHOTGUN
	}; // ADD POSSIBLE WEAPONS HERE.

	private int DAMAGE, RANGE;
	private double ATTACK_SPEED;
	WeaponInHand WEAPON_TYPE;

	public Weapon(WeaponInHand name, int damage, double attackspeed, int range, int cost) {

		// Use switch statement on WeaponInHand to set different values depending on
		// enum-value
		this.DAMAGE = damage;
		this.ATTACK_SPEED = attackspeed;
		this.RANGE = range;
		this.COST = cost;
		this.ITEM_TYPE = ItemType.Weapon;
		this.WEAPON_TYPE = name;
		//this.ID = id;

		setWeaponInHand();

	}

	public void setWeaponInHand(){
		switch (WEAPON_TYPE) {
			case SHOTGUN:
				this.NAME = "Shotgun";
				break;
			case SMG:
				this.NAME = "Submachine gun";
				break;
			case PISTOL:
				this.NAME = "Pistol";
				break;
			case SNIPER_RIFLE:
				this.NAME = "Sniper rifle";
				break;
			case ASSAULT_RIFLE:
				this.NAME = "Assault rifle";
				break;
			case SPACE_GUN:
				this.NAME = "Space gun";
				break;
			default:
				throw new IllegalStateException("Illegal weapon type!\n");
		}
	}

	public int getDamage() {
		return DAMAGE;
	}

	public double getAttackSpeed() {
		return ATTACK_SPEED;
	}

	public int getRange() {
		return RANGE;
	}
	
	public WeaponInHand getWeaponType() {
		return WEAPON_TYPE;
	}

}
