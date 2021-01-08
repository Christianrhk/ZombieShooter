package common.src.main;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie extends Entity {

	Animation zombieDown, zombieUp, zombieRight, zombieLeft;
	int AnimationSpeed;

	public Zombie() {
		super();
		this.HEALTH_POINTS = 20;
		this.ARMOR = 0;
		this.ATTACK_SPEED = 1.2;
		this.DAMAGE = 10;
		this.NAME = "ZOMBIE";
		this.POSITION = new Point(0, 0);
		
		this.AnimationSpeed = 32;
		
		try {
			this.IMAGE = ImageIO.read(new File("src/images/fat-zombie-png-32.png"));
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
		zombieDown = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 0, 32, 32, 32),
				Animation.cropImage(this.IMAGE, 1, 0, 32, 32, 32), Animation.cropImage(this.IMAGE, 2, 0, 32, 32, 32));
		zombieRight = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 1, 32, 32, 32), 
				Animation.cropImage(this.IMAGE, 1, 1, 32, 32, 32), Animation.cropImage(this.IMAGE, 2, 1, 32, 32, 32));
		zombieUp = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 2, 32, 32, 32), 
				Animation.cropImage(this.IMAGE, 1, 2, 32, 32, 32), Animation.cropImage(this.IMAGE, 2, 2, 32, 32, 32));
		zombieLeft = new Animation(this.AnimationSpeed, false, Animation.cropImage(this.IMAGE, 0, 3, 32, 32, 32), 
				Animation.cropImage(this.IMAGE, 1, 3, 32, 32, 32), Animation.cropImage(this.IMAGE, 2, 3, 32, 32, 32));
	}

}
