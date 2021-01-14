package common.src.main;

import java.awt.Point;

public class Bullet {
	
	int fireX, fireY;
	Point coords;
	int range;
	int speed;
	double angle;
	
	public Bullet(int x, int y, int range, int speed, double angle) {
		this.fireX = x;
		this.fireY = y;
		coords = new Point(x,y);
		this.range = range;
		this.speed = speed;
		this.angle = angle;
	}
	
	public void moveInDirection() {
		this.coords.x += this.speed * Math.cos(angle);
		this.coords.y += this.speed * Math.sin(angle);
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
