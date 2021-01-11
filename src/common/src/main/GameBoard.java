package common.src.main;

import javax.swing.*;
import org.jspace.Space;


import java.util.ArrayList;

public class GameBoard extends JFrame{


	private static final long serialVersionUID = 1L;
	int WIDTH, HEIGHT;
	Player p;
	JLayeredPane layeredBoard;

	GameLoop gl;
	ContentShop contentShop;

	// Multiplayer  constructor
	public GameBoard(int width, int height, Space playerSpace, String playerName, ArrayList<String> allNames) {

		setGameBoard(width, height, playerName);
		// adding content
		gl = new GameLoop(p, playerSpace, allNames);
		new Thread(gl).start();

		//insert shop as a layered board
		setLayeredBoard(width,height);
	}
	
	
	// Singleplayer constructor
	public GameBoard(int width, int height) { 
		
		setGameBoard(width, height, ""); // Singleplayer, no name needed for the communications protocols.
		
		// adding content
		gl = new GameLoop(p);
		new Thread(gl).start();

		//insert shop as a layered board
		setLayeredBoard(width,height);

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

	public void setLayeredBoard(int width, int height){

		//Setting players in layer 0
		gl.getContent().setBounds(0,0,width,height);
		layeredBoard.add(gl.getContent() , JLayeredPane.DEFAULT_LAYER); // layer 0

		//Adding shop
		contentShop = new ContentShop();

		//Setting shop on layer 1
		contentShop.setBounds(width/8,height/8,width-(width/4),height-(height/4));
		layeredBoard.add(contentShop,1);

		gl.getContent().addShop(contentShop);
		super.add(layeredBoard);

		// show Jframe
		setVisible(true);
	}


}

