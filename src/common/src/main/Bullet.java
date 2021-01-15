package common.src.main;

import java.awt.Point;

public class Bullet {

	int fireX, fireY, range, speed, damage;
	Point coords;
	double angle, attackspeed;


	public Bullet(int x, int y, int range, int speed, double angle, int damage, double attackspeed) {
		this.fireX = x;
		this.fireY = y;
		coords = new Point(x, y);
		this.range = range;
		this.speed = speed;
		this.angle = angle;
		this.damage = damage;
		this.attackspeed = attackspeed;

	}

	public void moveInDirection() {
		this.coords.x += this.speed * Math.cos(angle);
		this.coords.y += this.speed * Math.sin(angle);
	}
	
	public boolean outOfRange() {
		// Calculate distance from firing point
		double dist = Math.sqrt(Math.pow(this.fireX - this.coords.x, 2) + Math.pow(this.fireY - this.coords.y, 2));
		return dist > range;
	}

	public int getX() {
		return this.coords.x;
	}

	public int getY() {
		return this.coords.y;
	}

	public double getAngle() {
		return this.angle;
	}

}
