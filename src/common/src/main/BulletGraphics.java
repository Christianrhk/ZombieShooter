package common.src.main;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BulletGraphics {

	String bulletPath;
	BufferedImage bulletImage;

	public BulletGraphics() {
		this.bulletPath = "src/images/sBullet.png";

		try {
			this.bulletImage = ImageIO.read(new File(this.bulletPath));

		} catch (IOException e) {
			System.out.println("Couldn't get bullet image");
			e.printStackTrace();
		}

	}

	public void drawBullet(Graphics2D g2d, Bullet b) {

		int bulletSpawnX = 0, bulletSpawnY = 0;

		switch (b.getWeaponType()) {
			case SNIPER_RIFLE:
				bulletSpawnX = 58;
				bulletSpawnY = -11;
				break;
			case SMG:
				bulletSpawnX = 38;
				bulletSpawnY = -15;
				break;
			case SHOTGUN:
				bulletSpawnX = 46;
				bulletSpawnY = -15;	
				break;
			case PISTOL:
				bulletSpawnX = 34;
				bulletSpawnY = -15;	
				break;
			case ASSAULT_RIFLE:
				bulletSpawnX = 45;
				bulletSpawnY = -12;	
				break;
			case SPACE_GUN:
				bulletSpawnX = 20;
				bulletSpawnY = -10;	
				break;
		}
		

		int cx = 0;
		int cy = 0;
		AffineTransform oldAT = g2d.getTransform();
		g2d.translate(cx + b.getX(), cy + b.getY());
		g2d.rotate(b.getAngle());
		g2d.translate(-cx, -cy);
		g2d.drawImage(this.bulletImage, bulletSpawnX, bulletSpawnY, null);
		g2d.setTransform(oldAT);
	}

}
