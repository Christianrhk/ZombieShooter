package common.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerGraphics {
	String RUNLEFT_PATH, RUNRIGHT_PATH, IDLE_LEFT_PATH, IDLE_RIGHT_PATH;
	BufferedImage RUN_LEFT, RUN_RIGHT, IDLE_RIGHT, IDLE_LEFT;

	public static Animation idleLeft, idleRight, runningLeft, runningRight;
	int animationSpeed;
	int spriteSize;

	public PlayerGraphics() {
		this.spriteSize = 64;
		this.animationSpeed = 12;

		this.RUNRIGHT_PATH = "src/images/sPlayerRun_strip_right.png";
		this.RUNLEFT_PATH = "src/images/sPlayerRun_strip_left.png";
		this.IDLE_LEFT_PATH = "src/images/sPlayerIdle_strip_left.png";
		this.IDLE_RIGHT_PATH = "src/images/sPlayerIdle_strip.png";

		try {
			this.RUN_RIGHT = ImageIO.read(new File(this.RUNRIGHT_PATH));
			this.RUN_LEFT = ImageIO.read(new File(this.RUNLEFT_PATH));
			this.IDLE_LEFT = ImageIO.read(new File(this.IDLE_LEFT_PATH));
			this.IDLE_RIGHT = ImageIO.read(new File(this.IDLE_RIGHT_PATH));
		} catch (IOException e) {
			System.out.println("Couldn't get player image sheet");
			e.printStackTrace();
		}

		setSpriteSheetAnimations();
	}

	public void playerRunAnimation(Player p) {
		switch (p.dir) {
		case UP:
		case RIGHT:
			switch (p.mode) {
			case RUNNING:
				runningRight.runAnimation();
				break;
			case IDLE:
				idleRight.runAnimation();
				break;
			}
			break;
		case DOWN:
		case LEFT:
			switch (p.mode) {
			case RUNNING:
				runningLeft.runAnimation();
				break;
			case IDLE:
				idleLeft.runAnimation();
				break;
			}
			break;
		}

	}

	public void drawPlayer(Graphics g, Player p) {
		switch (p.dir) {
		case UP:
		case RIGHT:
			switch (p.mode) {
			case RUNNING:
				runningRight.drawAnimation(g, p.getX(), p.getY(), p.offset);
				break;
			case IDLE:
				idleRight.drawAnimation(g, p.getX(), p.getY(), p.offset);
				break;
			}
			break;
		case DOWN:
		case LEFT:
			switch (p.mode) {
			case RUNNING:
				runningLeft.drawAnimation(g, p.getX(), p.getY(), p.offset);
				break;
			case IDLE:
				idleLeft.drawAnimation(g, p.getX(), p.getY(), p.offset);
				break;
			}
			break;
		}
	}

	public void setSpriteSheetAnimations() {
		// Setting which sprites from spritesheet to use for a certain animation
		runningRight = getRunningAnimation(RUN_RIGHT);
		runningLeft = getRunningAnimation(RUN_LEFT);
		idleRight = getIdleAnimation(IDLE_RIGHT);
		idleLeft = getIdleAnimation(IDLE_LEFT);
	}

	public Animation getRunningAnimation(BufferedImage img) {
		return new Animation(this.animationSpeed, Animation.cropImage(img, 0, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 1, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 2, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 3, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 4, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 5, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 6, 0, spriteSize, spriteSize, spriteSize));
	}

	public Animation getIdleAnimation(BufferedImage img) {
		return new Animation(this.animationSpeed, Animation.cropImage(img, 0, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 1, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 2, 0, spriteSize, spriteSize, spriteSize),
				Animation.cropImage(img, 3, 0, spriteSize, spriteSize, spriteSize));
	}
}
