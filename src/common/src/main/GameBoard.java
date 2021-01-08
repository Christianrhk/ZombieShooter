package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard extends JFrame {

	int WIDTH, HEIGHT;
	Player p;

	// Multiplayer constructor
	public GameBoard(int width, int height, Space playerSpace, String playerName, ArrayList<String> allNames) {

		setGameBoard(width, height, playerName);
		
		// adding content
		super.add(new ContentsInFrame(p, playerSpace, allNames));

		// show Jframe
		setVisible(true);

	}
	
	// Singleplayer constructor
	public GameBoard(int width, int height) { 
		
		setGameBoard(width, height, ""); // Singleplayer, no name needed for the communications protocols.
		
		// adding content
		super.add(new ContentsInFrame(p));

		// show Jframe
		setVisible(true);
	}

	
	public void setGameBoard(int width, int height, String playername) {
		this.WIDTH = width;
		this.HEIGHT = height;
		
		// set jframe size etc.
		super.setSize(WIDTH, HEIGHT);
		super.setTitle("Zombie Shooter");
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creating player and setting position
		p = new Player(playername);
		p.POSITION.x = WIDTH/2;
		p.POSITION.y = HEIGHT/2;

	}


}

