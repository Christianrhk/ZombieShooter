package common.src.main;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Entity {
	
	String NAME;
	int HEALTH_POINTS, ARMOR, DAMAGE, MOVEMENT_SPEED;
	double ATTACK_SPEED;
	Point POSITION;
	int MAX_X, MAX_Y;
	
	BufferedImage IMAGE;
	String IMAGE_PATH;
	
	enum state {DEAD, ALIVE};
	state state;
	
	enum direction {UP, DOWN, LEFT, RIGHT};
	direction directionFacing;
	
	public Entity() {
		this.MOVEMENT_SPEED = 1;
		this.directionFacing = direction.DOWN;
		this.state = state.ALIVE;
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
			return true;
		}
		return false;
	}

	public boolean moveLeft() {
		if (this.POSITION.x > 0) {
			this.POSITION.x -= this.MOVEMENT_SPEED;
			this.directionFacing = direction.LEFT;
			return true;
		}
		return false;
	}
}
