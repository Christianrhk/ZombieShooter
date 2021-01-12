package common.src.main;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

	int MONEY;

	public Player(String name) {
		super();
		this.HEALTH_POINTS = 100;
		this.ARMOR = 50;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.NAME = name;
		this.POSITION = new Point(0, 0);
		this.MOVEMENT_SPEED = 2;
		this.MONEY = 0;
		try {
			this.IMAGE = ImageIO.read(new File("src/images/clown.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getX() {
		return this.POSITION.x;
	}

	public int getY() {
		return this.POSITION.y;
	}

	public int getHP() {
		return this.HEALTH_POINTS;
	}
	
	public int getArmor() {
		return this.ARMOR;
	}
	
	public int getMoney() {
		return this.MONEY;
	}

	// Take damage based on incoming damage and armor left
	public void takeDamage(int damage) {
		if (damage < this.ARMOR) {
			this.ARMOR -= damage;
		} else if (this.ARMOR > 0 && damage > this.ARMOR) {
			this.HEALTH_POINTS -= (damage - this.ARMOR);
			this.ARMOR = 0;
		} else {
			this.HEALTH_POINTS -= damage;
		}

		if (this.HEALTH_POINTS <= 0) {
			// Player dies
			// do something here
		}
	}

}
