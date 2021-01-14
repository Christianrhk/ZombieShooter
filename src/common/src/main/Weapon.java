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
		this.NAME = name.toString();
		this.ATTACK_SPEED = attackspeed;
		this.RANGE = range;
		this.RATE_OF_FIRE = rateOfFire;
		this.COST = cost;
		this.ID = id;
		this.ITEM_TYPE = ItemType.Weapon;
		this.WEAPON_TYPE = WeaponInHand.SPACE_GUN;

		try {
			Image img = ImageIO.read(new File("src/images/spacegun.png"));

			Icon icon = new ImageIcon(img);
			this.ICON = icon;
			// JLabel iconLabel = new JLabel(icon);
			// iconLabel.setBounds(115, 145, 59, 32);
		} catch (IOException e) {
			System.out.println("Icon for the armor item could not be located!\n");
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
