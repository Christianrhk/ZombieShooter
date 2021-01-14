package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import common.src.main.Entity.mode;

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
import java.util.List;


public class ContentsInFrame extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	// SoundHandler
	SoundHandler zombieSoundHandler;
	SoundHandler bulletSoundHandler;

	Space playerSpace;
	boolean playerPosChange[] = { false, false, false, false };
	boolean press[] = { false, false, false, false };
	BufferedImage bg;
	Space zombieSpace;
	
	
	// Graphic controllers
	ZombieGraphics ZG;
	PlayerGraphics PG;
	GunGraphics GG;

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
		
		// Sounds
		
		zombieSoundHandler = new SoundHandler();
		bulletSoundHandler = new SoundHandler();
		new Thread(zombieSoundHandler).start();
		new Thread(bulletSoundHandler).start();
		startBackGroundMusic();
		
		// Graphics
		ZG = new ZombieGraphics();
		PG = new PlayerGraphics();
		GG = new GunGraphics();
		this.p = player;
		this.name = player.NAME;

		// Get images
		try {
			bg = ImageIO.read(new File("src/images/zombiebanen.png"));
		} catch (IOException e) {
		}
		this.zombieSpace = zombieSpace;
		this.host = host;

		// this.zombie = new Zombie(100,100);
		this.playerSpace = playerSpace;
		if (host) {
			try {
				System.out.println("Put token in playerSpace");
				playerSpace.put("token");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void startBackGroundMusic() {
		zombieSoundHandler.playBackGroundMusic("src/sounds/backgroundMusic.WAV");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Draw background
		g2d.drawImage(bg, 0, 0, this);

		// Draw zombies
		drawAllZombies(g2d);

		// drawing other players
		drawAllPlayers(g2d);
		

	}

	private void drawAllPlayers(Graphics2D g2d) {
		try {
			playerSpace.get(new ActualField("token"));
			List<Object[]> getUpdate = playerSpace.queryAll(new FormalField(String.class), new FormalField(Player.class));
			for (Object[] o : getUpdate) {
				Player p = (Player) o[1];
				// g2d.drawImage(temp.IMAGE, temp.getX(), temp.getY(), this);
				//g2d.setColor(new Color(255, 0, 255));
				//g2d.fillRect(temp.POSITION.x, temp.POSITION.y, 15, 20);
				//GG.drawGun(g2d, p);
				GG.drawGun(g2d, p);
				PG.drawPlayer(g2d, p);
			}
			playerSpace.put("token");

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
				ZG.drawZombie(g2d, z);
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
			updatePlayer(p);
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

		if (host) {
			// Move zombies and animate
			ZombieController.moveZombies(p);
		}
	}

	public void subtractMoneyFromPlayer(int amount) {
		try {
			playerSpace.get(new ActualField("token"));
			Object[] o;
			o = playerSpace.get(new ActualField(name), new FormalField(Player.class));
			Player p = (Player) o[1];
			p.subtractMoney(amount);
			playerSpace.put(name, p);
			playerSpace.put("token");
		} catch (InterruptedException e) {
			System.out.println("Couldnt find player to subtract money from");
			e.printStackTrace();
		}
	}

	public void movePlayer() {
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
			updatePlayer(p);
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

    private void updatePlayer(Player p) {
        try {
            playerSpace.get(new ActualField("token"));
            playerSpace.get(new ActualField(name), new FormalField(Player.class));
            playerSpace.put(name,p);
            playerSpace.put("token");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		System.out.println("Mouse clicked at (" + x + "," + y + ")");

		bulletSoundHandler.playSound("src/sounds/aBullet.wav");
		try {

			zombieSpace.get(new ActualField("token"));
			java.util.List<Object[]> list = zombieSpace.getAll(new FormalField(Zombie.class));
			boolean dead;
			for (Object[] o : list) {
				dead = false;
				Zombie z = (Zombie) o[0];
				if (z.collision(x, y)) {
					// Zombie is hit!
					zombieSoundHandler.playSound("src/sounds/zombieDMG.wav");

					int damage = 10; // GET THIS FROM PLAYER WEAPON WHEN IMPLEMENTED <------
					if (z.takeDamage(damage)) {
						p.giveMoney(2);
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

			updatePlayer(p);
		} catch (InterruptedException e1) {
			System.out.println("I fail here ");
			e1.printStackTrace();
		}


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) { // Determine which way gun is pointing
		int x = GG.getGunPositionX();
		int y = GG.getGunPositionY();
		double deltax = e.getX() - x;
		double deltay = e.getY() - y;
		GG.setImageAngleRad(Math.atan2(deltay, deltax));
		
	}

}