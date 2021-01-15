package common.src.main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import common.src.main.Player.WeaponInHand;

public class Weapon extends item {

	private int DAMAGE, ATTACK_SPEED, RANGE, RATE_OF_FIRE;
	WeaponInHand WEAPON_TYPE;

	public Weapon(int id, WeaponInHand name, int damage, int attackspeed, int range, int rateOfFire, int cost) {

		// Use switch statement on WeaponInHand to set different values depending on
		// enum-value
		this.DAMAGE = damage;
		this.ATTACK_SPEED = attackspeed;
		this.RANGE = range;
		this.RATE_OF_FIRE = rateOfFire;
		this.COST = cost;
		this.ITEM_TYPE = ItemType.Weapon;
		this.WEAPON_TYPE = name;
		this.ID = id;

		setWeaponInHand();

	}

	private void setWeaponInHand(){
		switch (WEAPON_TYPE) {
			case SHOTGUN:
				this.NAME = "Shotgun";
				setGunImage("src/images/shotgunShop.png");
				break;
			case SMG:
				this.NAME = "Submachine gun";
				setGunImage("src/images/smg.png");
				break;
			case PISTOL:
				this.NAME = "Pistol";
				setGunImage("src/images/pistol.png");
				break;
			case SNIPER_RIFLE:
				this.NAME = "Sniper gun";
				setGunImage("src/images/sniperrifle.png");
				break;
			case ASSAULT_RIFLE:
				this.NAME = "Assault rifle";
				setGunImage("src/images/assaultrifleShop.png");
				break;
			case SPACE_GUN:
				this.NAME = "Space gun";
				setGunImage("src/images/spacegun.png");
				break;
			default:
				throw new IllegalStateException("Illegal weapon type!\n");
		}


	}

	private void setGunImage(String path){
		try {
			Image img = ImageIO.read(new File(path));
			Icon icon = new ImageIcon(img);
			this.ICON = icon;
		} catch (IOException e) {
			System.out.println(path + ": Icon could not be located!\n");
			e.printStackTrace();
		}


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
