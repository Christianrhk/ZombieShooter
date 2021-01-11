package common.src.main;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie extends Entity {

	Animation zombieDown, zombieUp, zombieRight, zombieLeft;
	int AnimationSpeed;
	int spriteSize;
	int offset;

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

		this.spriteSize = 64;
		this.offset = spriteSize/2;
		
		this.AnimationSpeed = 32;
		
		this.IMAGE_PATH = "src/images/fat-zombie-png-64.png";
		
		try {
			this.IMAGE = ImageIO.read(new File(this.IMAGE_PATH));
		} catch (IOException e) {
			System.out.println("Couldn't get zombie image sheet");
			e.printStackTrace();
		}
		
		setSpriteSheetAnimations();
	}
	
	public boolean isDead() {
		return this.HEALTH_POINTS <= 0;
	}
	
	public void damageZombie(int damage) {
		this.HEALTH_POINTS -= damage;
	}
	
	public boolean collision(int x, int y) {
		boolean hit = false;
		if(x > this.POSITION.x - this.offset && x < this.POSITION.x + this.offset && y > this.POSITION.y - this.offset && y < this.POSITION.y + this.offset){
			hit = true;
		}
		return hit;
	}

	public void zombieRunAnimation() {
		switch (this.directionFacing) {
		case DOWN:
			this.zombieDown.runAnimation();
			break;
		case UP:
			this.zombieUp.runAnimation();
			break;
		case RIGHT:
			this.zombieRight.runAnimation();
			break;
		case LEFT:
			this.zombieLeft.runAnimation();
			break;
		}
	}
	
	public void drawZombie(Graphics g) {
		switch (this.directionFacing) {
		case DOWN:
			this.zombieDown.drawAnimation(g, this.POSITION.x, this.POSITION.y, this.offset);
			break;
		case UP:
			this.zombieUp.drawAnimation(g, this.POSITION.x, this.POSITION.y, this.offset);
			break;
		case RIGHT:
			this.zombieRight.drawAnimation(g, this.POSITION.x, this.POSITION.y, this.offset);
			break;
		case LEFT:
			this.zombieLeft.drawAnimation(g, this.POSITION.x, this.POSITION.y, this.offset);
			break;
		}
	}

	public void setSpriteSheetAnimations() {
		// Setting which sprites from spritesheet to use for a certain animation
		zombieDown = getSpriteSheetRow(0);
		zombieRight = getSpriteSheetRow(1);
		zombieUp = getSpriteSheetRow(2);
		zombieLeft = getSpriteSheetRow(3);
	}
	
	public Animation getSpriteSheetRow(int row) {
		return new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, row, spriteSize, spriteSize, spriteSize), 
				Animation.cropImage(this.IMAGE, 1, row, spriteSize, spriteSize, spriteSize), Animation.cropImage(this.IMAGE, 2, row, spriteSize, spriteSize, spriteSize));
	}

}
