package common.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainMenu extends Canvas implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean multiplayerLobbyOpen = false;
	static JFrame frame;

	public MainMenu() {
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {

		Toolkit t = Toolkit.getDefaultToolkit();
		Image image = t.getImage("src/images/mainMenu.png");
		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (x > 165 && x < 635 && y > 170 && y < 285) {
			startSinglePlayer();
		} else if (x > 165 && x < 635 && y > 315 && y < 430) {
			startMultiPlayer();
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

	public void startSinglePlayer() {
		System.out.println("Singleplayer started");
		App.singlePlayer();
		disposeOfFrame();
	}

	public void startMultiPlayer() {
		System.out.println("Multiplayer started");
		if (!multiplayerLobbyOpen) {
			multiplayerLobbyOpen = true;
			LobbyWithSync.startLobby();
		}
	}

	public static void disposeOfFrame() {
		frame.setVisible(false);
	}

	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setTitle("Zombie Shooters - Main menu");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.add(menu);
		frame.setVisible(true);

		SoundHandler bgMusic = new SoundHandler();
		bgMusic.playBackGroundMusic("src/sounds/backgroundMusic.WAV");
	}
}
