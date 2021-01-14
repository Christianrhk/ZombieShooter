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

		switch (name) {

		case SHOTGUN:

			this.NAME = "Shotgun";

			try {
				Image img = ImageIO.read(new File("src/images/shotgunShop.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the SHOTGUN item could not be located!\n");
				e.printStackTrace();
			}

			break;

		case SMG:

			this.NAME = "Submachine gun";

			try {
				Image img = ImageIO.read(new File("src/images/smg.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the SMG item could not be located!\n");
				e.printStackTrace();
			}

			break;
		case PISTOL:

			this.NAME = "Pistol";

			try {
				Image img = ImageIO.read(new File("src/images/pistol.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the PISTOL item could not be located!\n");
				e.printStackTrace();
			}

			break;

		case SNIPER_RIFLE:

			this.NAME = "Sniper gun";

			try {
				Image img = ImageIO.read(new File("src/images/sniperrifle.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the SNIPER_GUN item could not be located!\n");
				e.printStackTrace();
			}

			break;

		case ASSAULT_RIFLE:

			this.NAME = "Assault rifle";

			try {
				Image img = ImageIO.read(new File("src/images/assaultrifleShop.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the ASSAULT_RIFLE_SHOP item could not be located!\n");
				e.printStackTrace();
			}

			break;

		case SPACE_GUN:

			this.NAME = "Space gun";

			try {
				Image img = ImageIO.read(new File("src/images/spacegun.png"));

				Icon icon = new ImageIcon(img);
				this.ICON = icon;
				// JLabel iconLabel = new JLabel(icon);
				// iconLabel.setBounds(115, 145, 59, 32);
			} catch (IOException e) {
				System.out.println("Icon for the SPACEGUN item could not be located!\n");
				e.printStackTrace();
			}

			break;

		default:
			throw new IllegalStateException("Illegal weapon type!\n");
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
