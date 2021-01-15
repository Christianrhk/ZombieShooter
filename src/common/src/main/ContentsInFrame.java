package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

import common.src.main.Entity.mode;
import common.src.main.Zombie.type;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContentsInFrame extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	// SoundHandler
	SoundHandler zombieSoundHandler;
	SoundHandler bulletSoundHandler;
	SoundHandler playerSoundHandler;

	Space playerSpace;
	boolean playerPosChange[] = { false, false, false, false };
	boolean press[] = { false, false, false, false };
	boolean shooting;
	BufferedImage bg;
	Space zombieSpace;
	Space bulletSpace;

	// Graphic controllers
	ZombieGraphics ZG, ZGElite;
	PlayerGraphics PG;
	GunGraphics GG;
	BulletGraphics BG;

	ContentShop shop;
	ContentOverlayHUD HUD;
	boolean shopVisible = false;

	boolean host;

	String name;

	Player p;

	// Constructor for multiplayer
	public ContentsInFrame(Player player, Space playerSpace, Space zombieSpace, boolean host) {
		super.setDoubleBuffered(true);
		addKeyListener(this);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		// Init Sounds
		zombieSoundHandler = new SoundHandler();
		bulletSoundHandler = new SoundHandler();
		playerSoundHandler = new SoundHandler();
		new Thread(zombieSoundHandler).start();
		new Thread(bulletSoundHandler).start();
		new Thread(playerSoundHandler).start();
		startBackGroundMusic();

		// Init Graphics
		ZG = new ZombieGraphics("src/images/fat-zombie-png-64.png");
		ZGElite = new ZombieGraphics("src/images/fat-zombie-elite.png");
		PG = new PlayerGraphics();
		GG = new GunGraphics();
		BG = new BulletGraphics();
		this.p = player;
		this.name = player.NAME;
		this.shooting = false;

		// Get images
		try {
			bg = ImageIO.read(new File("src/images/zombiebanen.png"));
		} catch (IOException e) {
		}
		this.zombieSpace = zombieSpace;
		this.host = host;

		this.bulletSpace = new SequentialSpace();
		this.playerSpace = playerSpace;
		try {
			bulletSpace.put("token");
			if (this.host) {
				this.playerSpace.put("token");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void startBackGroundMusic() {
		playerSoundHandler.playBackGroundMusic("src/sounds/backgroundMusic.WAV");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Draw background
		g2d.drawImage(bg, 0, 0, this);

		// Draw bullets
		drawAllBullets(g2d);

		// Draw zombies
		drawAllZombies(g2d);

		// drawing other players
		drawAllPlayers(g2d);

	}

	private void drawAllBullets(Graphics2D g2d) {
		try {
			bulletSpace.get(new ActualField("token"));
			List<Object[]> list = bulletSpace.queryAll(new FormalField(Bullet.class));
			bulletSpace.put("token");

			for (Object[] o : list) {
				Bullet b = (Bullet) o[0];
				BG.drawBullet(g2d, b);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void drawAllPlayers(Graphics2D g2d) {
		try {
			List<Player> players = getPlayerList();
			for (Player o : players) {

				GG.drawGun(g2d, o);
				PG.drawPlayer(g2d, o);
			}

		} catch (InterruptedException e) {
			System.out.println("failed to get token");
		}

	}

	private void drawAllZombies(Graphics2D g2d) {
		try {
			// System.out.println("I try to print the zombies ");
			zombieSpace.get(new ActualField("token"));
			List<Object[]> zombies = zombieSpace.queryAll(new FormalField(Zombie.class));

			for (Object[] o : zombies) {
				Zombie z = (Zombie) o[0];
				switch (z.getType()) {
				case NORMAL:
					ZG.drawZombie(g2d, z);
					break;
				case ELITE:
					ZGElite.drawZombie(g2d, z);
					break;
				}
			}

			zombieSpace.put("token");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/*
	 * To be able to verify if 2 keys are clicked simultaneously. When keys are
	 * released the boolean values will be set back to false.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		// System.out.println("Key Pressed!");
		// Switch on pressed keys
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			// Movement upwards
			// System.out.println("Up pressed");
			press[0] = true;
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			// Movement downwards
			// System.out.println("Down pressed");
			press[1] = true;
		}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			// Movement left
			// System.out.println("Left pressed");
			press[2] = true;
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			// Movement right
			// System.out.println("Right pressed");
			press[3] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			// Movement upwards
			// System.out.println("Up released");
			press[0] = false;
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			// Movement downwards
			// System.out.println("Down released");
			press[1] = false;
		}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			// Movement left
			// System.out.println("Left released");
			press[2] = false;
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			// Movement right
			// System.out.println("Right released");
			press[3] = false;
		}

		if (keyCode == KeyEvent.VK_B && shopVisible == false) {
			shopVisible = true;
			shop.setVisible(true);
			ContentShop.transactionState(true, p);
			updatePlayer();
		} else if (keyCode == KeyEvent.VK_B && shopVisible) {
			shopVisible = false;
			shop.setVisible(false);
			ContentShop.transactionState(false, p);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void updateGame() {
		// Move players
		movePlayer();

		if (shooting) {
			spawnBullets();
		}

		if (host) {
			// Move zombies and animate
			try {
				ZombieController.moveZombies(getPlayerList());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		List<Object[]> list = null;
		try {
			zombieSpace.get(new ActualField("token"));
			list = zombieSpace.queryAll(new FormalField(Zombie.class));
			zombieSpace.put("token");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Object[] o : list) {
			Zombie z = (Zombie) o[0];
			ZG.zombieRunAnimation(z);
			ZGElite.zombieRunAnimation(z);
		}

		moveBullets();
		checkPlayerCollision();
		checkBulletCollision();

	}

	// Check collision between the player and zombies
	private void checkPlayerCollision() {
		List<Object[]> list;
		try {
			zombieSpace.get(new ActualField("token"));
			list = zombieSpace.getAll(new FormalField(Zombie.class));
			for (Object[] o : list) {
				Zombie z = (Zombie) o[0];
				z.increaseDmgDelay();
				if (z.collision(p.getX(), p.getY())) {
					if (z.getDmgDelay() > 50) {
						playerSoundHandler.playSound("src/sounds/hit.wav");
						p.takeDamage(z.DAMAGE);
						this.HUD.updateArmor(p);
						this.HUD.updateHP(p);
						updatePlayer();
						z.resetDmgDelay();
					}
				}
				zombieSpace.put(z);
			}
			zombieSpace.put("token");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Check collision between each bullet and zombie
	private void checkBulletCollision() {
		List<Object[]> list;
		try {
			bulletSpace.get(new ActualField("token"));
			list = bulletSpace.getAll(new FormalField(Bullet.class));
			for (Object[] o : list) {
				Bullet b = (Bullet) o[0];
				if (!zombieBulletCollision(b.getX(), b.getY())) {
					bulletSpace.put(b);
				}
			}
			bulletSpace.put("token");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveBullets() {
		try {
			bulletSpace.get(new ActualField("token"));
			List<Object[]> list = bulletSpace.getAll(new FormalField(Bullet.class));
			for (Object[] o : list) {

				Bullet b = (Bullet) o[0];
				b.moveInDirection();

				// If bullet is still within the screen and has not travelled its full range
				// yet, put it back into the space
				if (b.getX() <= 800 && b.getY() <= 800 && b.getX() >= 0 && b.getY() >= 0 && !b.outOfRange())
					bulletSpace.put(b);
			}
			bulletSpace.put("token");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private List getPlayerList() throws InterruptedException {
		playerSpace.get(new ActualField("token"));
		List<Object[]> list = playerSpace.queryAll(new FormalField(String.class), new FormalField(Player.class));
		playerSpace.put("token");
		ArrayList<Player> players = new ArrayList<Player>();
		for (Object[] o : list) {
			players.add((Player) o[1]);
		}
		return players;

	}

	public void movePlayer() {

		PG.playerRunAnimation(p);
		p.bulletDelay++;
		// only update if a key has been pressed
		if (press[0] || press[1] || press[2] || press[3]) {
			if (press[0])
				playerPosChange[0] = p.moveUp();
			if (press[1])
				playerPosChange[1] = p.moveDown();
			if (press[2])
				playerPosChange[2] = p.moveLeft();
			if (press[3])
				playerPosChange[3] = p.moveRight();

			p.mode = mode.RUNNING;

		} else {
			p.mode = mode.IDLE;
		}
		updatePlayer();
		PG.playerRunAnimation(p);
	}

	public void addShop(ContentShop contentShop) {
		shop = contentShop;
		shop.setVisible(false);
	}

	public void addHUD(ContentOverlayHUD HUD) {
		this.HUD = HUD;
		this.HUD.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	private void updatePlayer() {
		try {
			playerSpace.get(new ActualField("token"));
			playerSpace.get(new ActualField(p.NAME), new FormalField(Player.class));
			playerSpace.put(p.NAME, p);
			playerSpace.put("token");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private boolean zombieBulletCollision(int x, int y) {
		boolean hit = false;
		try {
			zombieSpace.get(new ActualField("token"));
			List<Object[]> zombies = zombieSpace.getAll(new FormalField(Zombie.class));
			boolean dead;
			for (Object[] o : zombies) {
				dead = false;
				Zombie z = (Zombie) o[0];

				if (z.collision(x, y)) {
					hit = true;

					// Zombie is hit!
					zombieSoundHandler.playSound("src/sounds/zombieDMG.wav");

					int damage = p.getDamage();

					System.out.println("Hit zombie for " + damage + " damage");

					if (z.takeDamage(damage)) {
						if(z.getType() == type.NORMAL) {
							p.giveMoney(1);
						} else {
							p.giveMoney(3);
						}
						this.HUD.updateMoney(p);
						dead = true;
					}
				}
				if (dead) {
					zombieSpace.put("updateZombies");
				} else {
					zombieSpace.put(z);
				}
			}
			zombieSpace.put("token");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hit;
	}

	private void spawnBullets() {
		try {

			double as = p.getAttackSpeed();
			double count = 1.0 / as * 50.0; // 50 because there is 50 game ticks / second
			// System.out.println("Count is" + count + " AS is " + as);

			if (p.bulletDelay >= count) {
				bulletSpace.get(new ActualField("token"));
				switch (p.getWIH()) {
				case SHOTGUN:
					Bullet b1 = new Bullet(p.getX(), p.getY(), p.getWeaponRange(), 10, GG.getImageAngleRad() + 0.2,
							p.getDamage(), p.getAttackSpeed(), p.getWIH());
					Bullet b2 = new Bullet(p.getX(), p.getY(), p.getWeaponRange(), 10, GG.getImageAngleRad() - 0.2,
							p.getDamage(), p.getAttackSpeed(), p.getWIH());
					bulletSpace.put(b1);
					bulletSpace.put(b2);
				default:
					Bullet b = new Bullet(p.getX(), p.getY(), p.getWeaponRange(), 10, GG.getImageAngleRad(),
							p.getDamage(), p.getAttackSpeed(), p.getWIH());
					bulletSpace.put(b);
					break;
				}
				bulletSoundHandler.playSound("src/sounds/aBullet.wav");
				p.bulletDelay = 0;
				updatePlayer();
				bulletSpace.put("token");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.shooting = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.shooting = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = p.getX();
		int y = p.getY();
		double deltax = e.getX() - x;
		double deltay = e.getY() - y;
		GG.setImageAngleRad(Math.atan2(deltay, deltax));
	}

	@Override
	public void mouseMoved(MouseEvent e) { // Determine which way gun is pointing
		int x = p.getX();
		int y = p.getY();
		double deltax = e.getX() - x;
		double deltay = e.getY() - y;
		GG.setImageAngleRad(Math.atan2(deltay, deltax));
	}

}
