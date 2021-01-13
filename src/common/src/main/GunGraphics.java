package common.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GunGraphics {

	String SPACEGUN_PATH_LEFT, SPACEGUN_PATH_RIGHT;
	BufferedImage SPACEGUN_LEFT, SPACEGUN_RIGHT;

	Animation spaceGunRight, spaceGunLeft;

	public GunGraphics() {
		this.SPACEGUN_PATH_LEFT = "src/images/gun_left.png";
		this.SPACEGUN_PATH_RIGHT = "src/images/gun_right.png";
		
		try {
			this.SPACEGUN_RIGHT = ImageIO.read(new File(this.SPACEGUN_PATH_RIGHT));
			this.SPACEGUN_LEFT = ImageIO.read(new File(this.SPACEGUN_PATH_LEFT));

		} catch (IOException e) {
			System.out.println("Couldn't get gun images");
			e.printStackTrace();
		}
		
		setSpriteSheetAnimations();
	}

	@SuppressWarnings("incomplete-switch")
	public void drawGun(Graphics g, Player p) {

		boolean itsRight = true;

		switch (p.dir) {
		case LEFT:
			itsRight = false;
			break;
		case RIGHT:
			itsRight = true;
			break;
		}

		switch (p.weapon) {
		case SPACE_GUN:
			if (itsRight) {
				spaceGunRight.drawAnimation(g, p.getX()+p.offset, p.getY()+p.offset/2, p.offset);
			} else {
				spaceGunLeft.drawAnimation(g, p.getX()-p.offset, p.getY()+p.offset/2, p.offset);
			}
			break;
		}
	}
	
	public void setSpriteSheetAnimations() {
		// Setting which sprites from spritesheet to use for a certain animation
		spaceGunRight = getAnim(SPACEGUN_RIGHT);
		spaceGunLeft = getAnim(SPACEGUN_LEFT);
	}

	public Animation getAnim(BufferedImage img) {
		return new Animation(1, img);
	}
}
