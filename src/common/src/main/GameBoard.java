package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jspace.SequentialSpace;
import org.jspace.Space;


import java.util.ArrayList;

public class GameBoard extends JFrame{

	int WIDTH, HEIGHT;
	Player p;
	JLayeredPane layeredBoard;

	ContentsInFrame content;
	ContentShop contentShop;

	// Multiplayer constructor
	public GameBoard(int width, int height, Space playerSpace, String playerName, ArrayList<String> allNames) {

		setGameBoard(width, height, playerName);
		// adding content
		content = new ContentsInFrame(p, playerSpace, allNames);

		//insert shop as a layered board
		setLayeredBoard();


	}
	
	// Singleplayer constructor
	public GameBoard(int width, int height) { 
		
		setGameBoard(width, height, ""); // Singleplayer, no name needed for the communications protocols.
		
		// adding content
		content = new ContentsInFrame(p);

		//insert shop as a layered board
		setLayeredBoard();

	}

	
	public void setGameBoard(int width, int height, String playername) {
		this.WIDTH = width;
		this.HEIGHT = height;
		
		// set jframe size etc.
		super.setSize(WIDTH, HEIGHT);
		super.setTitle("Zombie Shooter");
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//create layered pane
		layeredBoard = new JLayeredPane();


		
		//Creating player and setting position
		p = new Player(playername);
		p.POSITION.x = WIDTH/2;
		p.POSITION.y = HEIGHT/2;

	}

	public void setLayeredBoard(){
		content.setBounds(0,0,800,800);
		layeredBoard.add(content , JLayeredPane.DEFAULT_LAYER); // layer 0


		contentShop = new ContentShop();
		contentShop.setBounds(100,100,500,500);
		layeredBoard.add(contentShop,1);

		content.addShop(contentShop);
		super.add(layeredBoard);

		// show Jframe
		setVisible(true);
	}


}

