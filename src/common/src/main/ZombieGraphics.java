package common.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ZombieGraphics {
	String IMAGE_PATH;
	BufferedImage IMAGE;
	
	public static Animation zombieDown, zombieUp, zombieRight, zombieLeft;
	int AnimationSpeed;
	int spriteSize;
	
	public ZombieGraphics() {
		this.spriteSize = 64;
		
		this.AnimationSpeed = 16;
		
		this.IMAGE_PATH = "src/images/fat-zombie-png-64.png";
		
		try {
			this.IMAGE = ImageIO.read(new File(this.IMAGE_PATH));
		} catch (IOException e) {
			System.out.println("Couldn't get zombie image sheet");
			e.printStackTrace();
		}
		setSpriteSheetAnimations();
	}
	
	public static void zombieRunAnimation(Zombie z) {
		//System.out.println(this.directionFacing);
		switch (z.directionFacing) {
		case DOWN:
			zombieDown.runAnimation();
			break;
		case UP:
			zombieUp.runAnimation();
			break;
		case RIGHT:
			zombieRight.runAnimation();
			break;
		case LEFT:
			zombieLeft.runAnimation();
			break;
		}
	}
	
	public void drawZombie(Graphics g, Zombie z) {
		switch (z.directionFacing) {
		case DOWN:
			ZombieGraphics.zombieDown.drawAnimation(g, z.POSITION.x, z.POSITION.y, z.offset);
			break;
		case UP:
			ZombieGraphics.zombieUp.drawAnimation(g, z.POSITION.x, z.POSITION.y, z.offset);
			break;
		case RIGHT:
			ZombieGraphics.zombieRight.drawAnimation(g, z.POSITION.x, z.POSITION.y, z.offset);
			break;
		case LEFT:
			ZombieGraphics.zombieLeft.drawAnimation(g, z.POSITION.x, z.POSITION.y, z.offset);
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
