package common.src.main;

import java.awt.Point;

public class Entity {
	
	String NAME;
	int HEALTH_POINTS, ARMOR, DAMAGE, MOVEMENT_SPEED;
	double ATTACK_SPEED, MONEY;
	Point POSITION;

	// Direction.
	
	public Entity() {
		HEALTH_POINTS = 100;
		ARMOR = 50;
		DAMAGE = 10;
		ATTACK_SPEED = 1.2;
		MOVEMENT_SPEED = 10;
		MONEY = 0;
		POSITION = new Point(0,0);
	}
	
}
