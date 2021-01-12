package common.src.main;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie extends Entity {
	boolean HIT;// Used to determine if a zombie has been hit by a player

	public Zombie(int x, int y) {
		super();
		this.POSITION = new Point(x, y);
		initZombie();
	}
	
	public Zombie(Point p) {
		super();
		this.POSITION = p;
		initZombie();
	}
	
	public void initZombie() {
		this.HEALTH_POINTS = 20;
		this.ARMOR = 0;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.NAME = "ZOMBIE";
		this.HIT = false;
		this.offset = 32;
	}
	
	public boolean isDead() {
		return this.HEALTH_POINTS <= 0;
	}
	
	public void damageZombie(int damage) {
		this.HEALTH_POINTS -= damage;
		this.HIT = true;
	}
	
	public boolean collision(int x, int y) {
		boolean hit = false;
		if(x > this.POSITION.x - this.offset && x < this.POSITION.x + this.offset && y > this.POSITION.y - this.offset && y < this.POSITION.y + this.offset){
			hit = true;
		}
		return hit;
	}

}
