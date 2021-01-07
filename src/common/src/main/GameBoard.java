package common.src.main;

import javax.swing.*;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JFrame {

	int WIDTH, HEIGHT;
	Player p;

	// Multiplayer constructor
	public GameBoard(int width, int height, Space playerSpace, String playerName, ArrayList<String> allNames) {

		this.WIDTH = width;
		this.HEIGHT = height;
		
		// set jframe size etc.
		super.setSize(WIDTH, HEIGHT);
		super.setTitle("Zombie Shooter");
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p = new Player(playerName);
		p.POSITION.x = WIDTH/2;
		p.POSITION.y = HEIGHT/2;

		// adding content
		super.add(new ContentsInFrame(p, playerSpace, allNames));

		// show Jframe
		setVisible(true);

	}
	
	// Singleplayer constructor
	public GameBoard(int width, int height) { 
		this.WIDTH = width;
		this.HEIGHT = height;
		
		super.setSize(WIDTH, HEIGHT);
		super.setTitle("Zombie Shooter");
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p = new Player("");
		p.POSITION.x = WIDTH/2;
		p.POSITION.y = HEIGHT/2;
		
		// adding content
		super.add(new ContentsInFrame(p));

		// show Jframe
		setVisible(true);
	}



}
