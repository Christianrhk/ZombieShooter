package common.src.main;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

	Player p;

	public GameBoard(int width, int height) {

		// set jframe size etc.
		super.setSize(width, height);
		super.setTitle("Zombie Shooter");
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		p = new Player("Kurt");

		// adding content
		super.add(new ContentsInFrame(p));

		// show Jframe
		setVisible(true);

	}

	public static void main(String[] args) {
		new GameBoard(800, 800);

	}

}
