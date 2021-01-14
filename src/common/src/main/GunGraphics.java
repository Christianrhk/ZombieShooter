package common.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GunGraphics {

	String SPACEGUN_PATH;
	BufferedImage SPACEGUN;
	
	int imagePositionX;
	int imagePositionY;
	
	public double imageAngleRad;

	public GunGraphics() {
		this.SPACEGUN_PATH = "src/images/spacegun.png";
		
		try {
			this.SPACEGUN = ImageIO.read(new File(this.SPACEGUN_PATH));

		} catch (IOException e) {
			System.out.println("Couldn't get gun image");
			e.printStackTrace();
		}
		
		imagePositionX = 0;
		imagePositionY = 0;
		imageAngleRad = 0;
	}
	
	public int getGunPositionX() {
		return this.imagePositionX;
	}
	
	public int getGunPositionY() {
		return this.imagePositionY;
	}
	
	public void setImageAngleRad(double angle) {
		this.imageAngleRad = angle;
	}
	
	public double getImageAngleRad() {
		return this.imageAngleRad;
	}

	
	public void drawGun(Graphics2D g2d, Player p) {
		
		imagePositionY = p.getY();
		imagePositionX = p.getX();
		
		int cx = 0;
		int cy = 0;
		AffineTransform oldAT = g2d.getTransform();
		g2d.translate(cx+getGunPositionX(), cy+getGunPositionY());
		g2d.rotate(getImageAngleRad());
		g2d.translate(-cx, -cy);
		g2d.drawImage(SPACEGUN, 0,-(SPACEGUN.getHeight()/2), null);
		g2d.setTransform(oldAT);
	}
	
}
