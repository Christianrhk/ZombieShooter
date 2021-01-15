package common.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GunGraphics {

	String SPACEGUN_PATH, PISTOL_PATH, SNIPER_PATH, ASSAULTRIFLE_PATH, SMG_PATH, SHOTGUN_PATH;
	BufferedImage SPACEGUN, PISTOL, SNIPER, ASSAULTRIFLE, SMG, SHOTGUN;

	int imagePositionX;
	int imagePositionY;

	public double imageAngleRad;

	public GunGraphics() {
		this.SPACEGUN_PATH = "src/images/spacegun.png";
		this.PISTOL_PATH = "src/images/pistol.png";
		this.SNIPER_PATH = "src/images/sniperrifle.png";
		this.ASSAULTRIFLE_PATH = "src/images/assaultrifle.png";
		this.SMG_PATH = "src/images/smg.png";
		this.SHOTGUN_PATH = "src/images/shotgun.png";

		try {
			this.SPACEGUN = ImageIO.read(new File(this.SPACEGUN_PATH));
			this.PISTOL = ImageIO.read(new File(this.PISTOL_PATH));
			this.SNIPER = ImageIO.read(new File(this.SNIPER_PATH));
			this.ASSAULTRIFLE = ImageIO.read(new File(this.ASSAULTRIFLE_PATH));
			this.SMG = ImageIO.read(new File(this.SMG_PATH));
			this.SHOTGUN = ImageIO.read(new File(this.SHOTGUN_PATH));

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
		
		BufferedImage img = null;

		switch (p.weapon.getWeaponType()) {
		case SPACE_GUN:
			img = SPACEGUN;
			break;
		case SNIPER_RIFLE:
			img = SNIPER;
			break;
		case ASSAULT_RIFLE:
			img = ASSAULTRIFLE;
			break;
		case PISTOL:
			img = PISTOL;
			break;
		case SMG:
			img = SMG;
			break;
		case SHOTGUN:
			img = SHOTGUN;
			break;
		default:
			img = PISTOL;
		}

		int cx = 0;
		int cy = 0;
		AffineTransform oldAT = g2d.getTransform();
		g2d.translate(cx + getGunPositionX(), cy + getGunPositionY());
		g2d.rotate(getImageAngleRad());
		g2d.translate(-cx, -cy);
		g2d.drawImage(img, 0, -(img.getHeight() / 2), null);
		g2d.setTransform(oldAT);
	}

}
