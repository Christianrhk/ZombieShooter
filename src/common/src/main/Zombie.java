package common.src.main;

import java.awt.Point;

public class Zombie extends Entity {
	public Zombie() {
		super();
		this.HEALTH_POINTS = 20;
		this.ARMOR = 0;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.MOVEMENT_SPEED = 1;
		this.NAME = "ZOMBIE";
		this.POSITION = new Point(0, 0);
	}
	
}
