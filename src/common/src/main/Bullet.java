package common.src.main;

import java.awt.Point;

import common.src.main.Weapon.WeaponInHand;

public class Bullet {

	int fireX, fireY, range, speed, damage;
	double x, y;
	double angle, attackspeed;
	
	WeaponInHand type;


	public Bullet(int x, int y, int range, int speed, double angle, int damage, double attackspeed, WeaponInHand type) {
		this.fireX = x;
		this.fireY = y;
		this.x = x;
		this.y = y;
		this.range = range;
		this.speed = speed;
		this.angle = angle;
		this.damage = damage;
		this.attackspeed = attackspeed;
		this.type = type;
	}

	public void moveInDirection() {
		this.x += this.speed * Math.cos(angle);
		this.y += this.speed * Math.sin(angle);
	}
	
	public boolean outOfRange() {
		// Calculate distance from firing point
		double dist = Math.sqrt(Math.pow(this.fireX - getX(), 2) + Math.pow(this.fireY - getY(), 2));
		return dist > range;
	}

	public int getX() {
		return (int)this.x;
	}

	public int getY() {
		return (int)this.y;
	}

	public double getAngle() {
		return this.angle;
	}
	
	public WeaponInHand getWeaponType() {
		return this.type;
	}

}
