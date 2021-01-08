package common.src.main;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

	int player_offset = 0;
	int MAX_X = 800;
	int MAX_Y = 800;

	public Player(String name) {
		super();
		this.HEALTH_POINTS = 100;
		this.ARMOR = 50;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.NAME = name;
		this.POSITION = new Point(0, 0);
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

	public boolean moveUp() {
		if (this.POSITION.y > 0 + player_offset) {
			this.POSITION.y -= this.MOVEMENT_SPEED;
			return true;
		}
		return false;
	}

	public boolean moveDown() {
		if (this.POSITION.y < MAX_Y - player_offset) {
			this.POSITION.y += this.MOVEMENT_SPEED;
			return true;
		}
		return false;
	}

	public boolean moveRight() {
		if (this.POSITION.x < MAX_X - player_offset) {
			this.POSITION.x += this.MOVEMENT_SPEED;
			return true;
		}
		return false;
	}

	public boolean moveLeft() {
		if (this.POSITION.x > 0 + player_offset) {
			this.POSITION.x -= this.MOVEMENT_SPEED;
			return true;
		}
		return false;
	}
	
	// Take damage based on incoming damage and armor left
	public void takeDamage(int damage) {
		if(damage < this.ARMOR) {
			this.ARMOR -= damage;
		} else if(this.ARMOR > 0 && damage > this.ARMOR){
			this.HEALTH_POINTS -= (damage - this.ARMOR);
			this.ARMOR = 0;
		} else {
			this.HEALTH_POINTS -= damage;
		}
		
		if(this.HEALTH_POINTS <= 0) {
			// Player dies
			// do something here
		}
	}
}
