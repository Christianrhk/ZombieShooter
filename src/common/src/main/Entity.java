package common.src.main;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Entity {
	
	String NAME;
	int HEALTH_POINTS, ARMOR, DAMAGE, MOVEMENT_SPEED;
	double ATTACK_SPEED;
	Point POSITION;
	
	BufferedImage IMAGE;
	String IMAGE_PATH;
	
	
	enum direction {UP, DOWN, LEFT, RIGHT};
	direction directionFacing;
	
	public Entity() {
		this.MOVEMENT_SPEED = 1;
		this.directionFacing = direction.DOWN;
	}
	
}
