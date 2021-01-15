package common.src.main;

public class StandardWeapons {
	
	public static Weapon pistol, sniper, smg, shotgun, assaultrifle, spacegun;
	
	// public Weapon(WeaponInHand name, int damage, int attackspeed, int range, int cost) 
	public StandardWeapons() {
		pistol = new Weapon(Weapon.WeaponInHand.PISTOL, 3, 1, 400, 10);
		sniper = new Weapon(Weapon.WeaponInHand.SNIPER_RIFLE, 50, 0.2, 800, 50);
		smg = new Weapon(Weapon.WeaponInHand.SMG, 3, 3, 250, 25);
		shotgun = new Weapon(Weapon.WeaponInHand.SHOTGUN, 6, 1, 70, 15);
		assaultrifle = new Weapon(Weapon.WeaponInHand.ASSAULT_RIFLE, 5, 2, 500, 30);
		spacegun = new Weapon(Weapon.WeaponInHand.SPACE_GUN, 100, 10, 800, 10000);
	}

	public static Weapon getSpacegun() {
		return spacegun;
	}

	public static Weapon getAssaultrifle() {
		return assaultrifle;
	}

	public static Weapon getShotgun() {
		return shotgun;
	}

	public static Weapon getSmg() {
		return smg;
	}

	public static Weapon getSniper() {
		return sniper;
	}

	public static Weapon getPistol() {
		return pistol;
	}
	
}
