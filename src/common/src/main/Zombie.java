package common.src.main;
import java.awt.Point;

public class Zombie extends Entity {
	boolean HIT;// Used to determine if a zombie has been hit by a player
	int damageDelay;
	public Zombie(int x, int y) {
		super();
		
		this.damageDelay = 0;
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
		this.DAMAGE = 10;
		this.NAME = "ZOMBIE";
		this.HIT = false;
		this.offset = 32;
	}

	public boolean collision(int x, int y) {
		boolean hit = false;
		if(x > this.POSITION.x - this.offset && x < this.POSITION.x + this.offset && y > this.POSITION.y - this.offset && y < this.POSITION.y + this.offset){
			hit = true;
		}
		return hit;
	}
	
	public int getDmgDelay() {
		return this.damageDelay;
	}
	
	public void resetDmgDelay() {
		this.damageDelay = 0;
	}
	
	public void increaseDmgDelay() {
		this.damageDelay++;
	}

}
