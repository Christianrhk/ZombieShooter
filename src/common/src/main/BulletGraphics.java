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

		int cx = 0;
		int cy = 0;
		AffineTransform oldAT = g2d.getTransform();
		g2d.translate(cx+b.getX(), cy+b.getY());
		g2d.rotate(b.getAngle());
		g2d.translate(-cx, -cy);
		g2d.drawImage(bulletImage, 20,-10, null);
		g2d.setTransform(oldAT);
		//g2d.drawImage(bulletImage, b.coords.x, b.coords.y, null);
	}

}
