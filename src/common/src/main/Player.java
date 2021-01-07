package common.src.main;

import java.awt.Point;

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
		this.MOVEMENT_SPEED = 1;
		this.NAME = name;
		this.POSITION = new Point(0, 0);
	}
	
	public int getX() {
		return this.POSITION.x;
	}
	
	public int getY() {
		return this.POSITION.y;
	}

	public void moveUp() {
		if (this.POSITION.y > 0 + player_offset) {
			this.POSITION.y -= this.MOVEMENT_SPEED;
		}
	}

	public void moveDown() {
		if (this.POSITION.y < MAX_Y - player_offset) {
			this.POSITION.y += this.MOVEMENT_SPEED;
		}
	}

	public void moveRight() {
		if (this.POSITION.x < MAX_X - player_offset) {
			this.POSITION.x += this.MOVEMENT_SPEED;
		}
	}

	public void moveLeft() {
		if (this.POSITION.x > 0 + player_offset) {
			this.POSITION.x -= this.MOVEMENT_SPEED;
		}
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
