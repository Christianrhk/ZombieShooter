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

	public Zombie() {
		super();
		this.HEALTH_POINTS = 20;
		this.ARMOR = 0;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.NAME = "ZOMBIE";
		this.POSITION = new Point(0, 0);
		
		this.spriteSize = 64;
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
	
	public void drawZombie(Graphics g, int x, int y) {
		switch (this.directionFacing) {
		case DOWN:
			this.zombieDown.drawAnimation(g, x, y);
			break;
		case UP:
			this.zombieUp.drawAnimation(g, x, y);
			break;
		case RIGHT:
			this.zombieRight.drawAnimation(g, x, y);
			break;
		case LEFT:
			this.zombieLeft.drawAnimation(g, x, y);
			break;
		}
	}

	public void setSpriteSheetAnimations() {
		// Setting which sprites from spritesheet to use for a certain animation
		zombieDown = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(this.IMAGE, 1, 0, spriteSize, spriteSize, spriteSize), Animation.cropImage(this.IMAGE, 2, 0, spriteSize, spriteSize, spriteSize));
		zombieRight = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 1, spriteSize, spriteSize, spriteSize), 
				Animation.cropImage(this.IMAGE, 1, 1, spriteSize, spriteSize, spriteSize), Animation.cropImage(this.IMAGE, 2, 1, spriteSize, spriteSize, spriteSize));
		zombieUp = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 2, spriteSize, spriteSize, spriteSize), 
				Animation.cropImage(this.IMAGE, 1, 2, spriteSize, spriteSize, spriteSize), Animation.cropImage(this.IMAGE, 2, 2, spriteSize, spriteSize, spriteSize));
		zombieLeft = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 3, spriteSize, spriteSize, spriteSize), 
				Animation.cropImage(this.IMAGE, 1, 3, spriteSize, spriteSize, spriteSize), Animation.cropImage(this.IMAGE, 2, 3, spriteSize, spriteSize, spriteSize));
	}

}
