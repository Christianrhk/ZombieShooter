package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ContentsInFrame extends JPanel implements KeyListener, ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	boolean multiplayer = false;

	Player p;
	// Boolean playerPosChange = false;
	ArrayList<String> allNames; // Names of all other players, used for communication
	Space space;
	boolean playerPosChange[] = { false, false, false, false };
	boolean press[] = { false, false, false, false };
	Point player2;
	//Timer t;
	BufferedImage bg;

	ContentShop shop;
	boolean shopVisible = false;

	// Contructor for singleplayer
	public ContentsInFrame(Player p) {
		initContentsInFrame(p);
		multiplayer = false;

		// this.zombie = new Zombie(50,50);
	}

	// Constructor for multiplayer
	public ContentsInFrame(Player p, Space playerSpace, ArrayList<String> allNames) {
		initContentsInFrame(p);

		player2 = new Point(p.getX(), p.getY());

		// this.zombie = new Zombie(100,100);

		this.space = playerSpace;
		this.allNames = allNames;

		multiplayer = true;
	}

	// Shared initialization for multiplayer and singleplayer
	public void initContentsInFrame(Player p) {
		super.setDoubleBuffered(true);
		addKeyListener(this);
		setFocusable(true);
		addMouseListener(this);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		// setting timer
		//t = new Timer(4, this);
		//t.start();

		this.p = p;

		// Get background picture
		try {
			bg = ImageIO.read(new File("src/images/zombiebanen.png"));
		} catch (IOException e) {
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Draw background
		g2d.drawImage(bg, 0, 0, this);

		// Draw player
		g2d.drawImage(p.IMAGE, p.getX(), p.getY(), this);

		// Draw zombies
		if (!multiplayer) {
			for (Zombie z : ZombieController.zombies) {
				z.drawZombie(g2d);
			}
		}

		// drawing other players
		if (multiplayer) {
			g2d.setColor(new Color(255, 0, 255));
			g2d.fillRect(player2.x, player2.y, 15, 20);
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
		System.out.println("Key Pressed!");
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
		} else if (keyCode == KeyEvent.VK_B && shopVisible) {
			shopVisible = false;
			shop.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void updateGame() {
		// Move players
		movePlayer();

		if (!multiplayer) {
			// Move zombies
			ZombieController.moveZombies(p);

			// Animate zombies
			for (Zombie z : ZombieController.zombies) {
				z.zombieRunAnimation();
			}
		}

		// send player position and get other players position
		if (multiplayer) {
			sendUpdateToOtherPlayers();
			tryToUpdateOtherPlayers();
		}
	}

	public void sendUpdateToOtherPlayers() {
		// only update if player position has changed
		if (playerPosChange[0] || playerPosChange[1] || playerPosChange[2] || playerPosChange[3]) {
			try {
				for (String name : allNames) { // send update to all players
					space.put("PLAYERUPDATE", name, p.getX(), p.getY());
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		playerPosChange[0] = playerPosChange[1] = playerPosChange[2] = playerPosChange[3] = false;
	}

	public void tryToUpdateOtherPlayers() {
		try {
			Object[] otherPosition = space.getp(new ActualField("PLAYERUPDATE"), new ActualField(p.NAME),
					new FormalField(Integer.class), new FormalField(Integer.class));
			if (otherPosition != null) {
				// System.out.println("Got position : " + otherPosition[2] + " " +
				// otherPosition[3]);
				player2.x = (int) otherPosition[2];
				player2.y = (int) otherPosition[3];
			} else {
				// System.out.println("Position is null");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void movePlayer() {
		if (press[0])
			playerPosChange[0] = p.moveUp();
		if (press[1])
			playerPosChange[1] = p.moveDown();
		if (press[2])
			playerPosChange[2] = p.moveLeft();
		if (press[3])
			playerPosChange[3] = p.moveRight();
	}

	public void addShop(ContentShop contentShop) {
		shop = contentShop;
		shop.setVisible(false);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse clicked at: " + x + ", " + y);
		for (Zombie z : ZombieController.zombies) {
			if (z.collision(x, y)) {
				z.damageZombie(20);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
}
