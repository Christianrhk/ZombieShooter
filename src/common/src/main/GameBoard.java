package common.src.main;

import javax.swing.*;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JFrame {

	Player p;

	public GameBoard(int width, int height, Space playerSpace, String playerName, ArrayList<String> allNames) {

		// set jframe size etc.
		super.setSize(width, height);
		super.setTitle("Zombie Shooter");
		super.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p = new Player(playerName);

		// adding content
		super.add(new ContentsInFrame(p, playerSpace, allNames));

		// show Jframe
		setVisible(true);

	}

//	public static void main(String[] args) {
//		new GameBoard(800, 800, new SequentialSpace(),"KURT!");
//
//	}

}
