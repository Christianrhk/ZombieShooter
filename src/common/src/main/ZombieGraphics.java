package common.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ZombieGraphics {
	String IMAGE_PATH;
	BufferedImage IMAGE;
	
	Animation zombieDown, zombieUp, zombieRight, zombieLeft;
	int AnimationSpeed;
	int spriteSize;
	Zombie z;
	
	public ZombieGraphics(Zombie z) {

		this.z = z;
		
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
		//System.out.println(this.directionFacing);
		switch (this.z.directionFacing) {
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
		switch (this.z.directionFacing) {
		case DOWN:
			this.zombieDown.drawAnimation(g, this.z.POSITION.x, this.z.POSITION.y, this.z.offset);
			break;
		case UP:
			this.zombieUp.drawAnimation(g, this.z.POSITION.x, this.z.POSITION.y, this.z.offset);
			break;
		case RIGHT:
			this.zombieRight.drawAnimation(g, this.z.POSITION.x, this.z.POSITION.y, this.z.offset);
			break;
		case LEFT:
			this.zombieLeft.drawAnimation(g, this.z.POSITION.x, this.z.POSITION.y, this.z.offset);
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
		return new Animation(this.AnimationSpeed, Animation.cropImage(this.IMAGE, 0, row, spriteSize, spriteSize, spriteSize), 
				Animation.cropImage(this.IMAGE, 1, row, spriteSize, spriteSize, spriteSize), Animation.cropImage(this.IMAGE, 2, row, spriteSize, spriteSize, spriteSize));
	}
}
