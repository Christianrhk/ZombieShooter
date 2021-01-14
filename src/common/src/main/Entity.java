package common.src.main;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Entity {
	
	String NAME;
	int HEALTH_POINTS, ARMOR, DAMAGE, MOVEMENT_SPEED;
	double ATTACK_SPEED;
	Point POSITION;
	int MAX_X, MAX_Y;
		
	int offset;
	
	enum state {DEAD, ALIVE};
	enum mode {RUNNING, IDLE};
	state state;
	mode mode;
	
	enum direction {UP, DOWN, LEFT, RIGHT};
	direction directionFacing;
	direction dir;
	
	@SuppressWarnings("static-access")
	public Entity() {
		this.MOVEMENT_SPEED = 1;
		this.directionFacing = direction.DOWN;
		this.dir = direction.RIGHT;
		this.state = state.ALIVE;
		this.mode = mode.IDLE;

		MAX_X = 800;
		MAX_Y = 800;
	}
	
	public boolean moveUp() {
		if (this.POSITION.y > 0) {
			this.POSITION.y -= this.MOVEMENT_SPEED;
			this.directionFacing = direction.UP;
			return true;
		}
		return false;
	}

	public boolean moveDown() {
		if (this.POSITION.y < MAX_Y) {
			this.POSITION.y += this.MOVEMENT_SPEED;
			this.directionFacing = direction.DOWN;
			return true;
		}
		return false;
	}

	public boolean moveRight() {
		if (this.POSITION.x < MAX_X) {
			this.POSITION.x += this.MOVEMENT_SPEED;
			this.directionFacing = direction.RIGHT;
			this.dir = direction.RIGHT;
			return true;
		}
		return false;
	}

	public boolean moveLeft() {
		if (this.POSITION.x > 0) {
			this.POSITION.x -= this.MOVEMENT_SPEED;
			this.directionFacing = direction.LEFT;
			this.dir = direction.LEFT;
			return true;
		}
		return false;
	}
	
	// Take damage based on incoming damage and armor left
		public boolean takeDamage(int damage) { //Return true if dead
			if (damage < this.ARMOR) {
				this.ARMOR -= damage;
			} else if (this.ARMOR > 0 && damage > this.ARMOR) {
				this.HEALTH_POINTS -= (damage - this.ARMOR);
				this.ARMOR = 0;
			} else {
				this.HEALTH_POINTS -= damage;
			}

			if (this.HEALTH_POINTS <= 0) {
				return true;
			} else {
				return false;
			}
		}
}
