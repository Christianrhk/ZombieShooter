package common.src.main;

import javax.swing.*;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;


import java.util.ArrayList;

public class GameBoard extends JFrame {


    private static final long serialVersionUID = 1L;
    int WIDTH, HEIGHT;
    JLayeredPane layeredBoard;

    GameLoop gl;
    ContentShop contentShop;
    ContentOverlayHUD HUD;

    // Multiplayer  constructor
    public GameBoard(int width, int height, Space playerSpace, String playerName, Space zombieSpace, boolean host) {

        setGameBoard(width, height, playerName, playerSpace, host);

        if (host) super.setTitle("Zombie Shooter - host");
        // adding content
        gl = new GameLoop(playerName, playerSpace, zombieSpace, host);
        new Thread(gl).start();

        //insert shop as a layered board
        try {
            Object[] o = playerSpace.query(new ActualField(playerName), new FormalField(Player.class));
            setLayeredBoard(width, height, (Player) o[1]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void setGameBoard(int width, int height, String playerName, Space playerSpace, boolean host) {
        this.WIDTH = width;
        this.HEIGHT = height;

        // set jframe size etc.
        super.setSize(WIDTH, HEIGHT);
        super.setTitle("Zombie Shooter");
        super.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create layered pane
        layeredBoard = new JLayeredPane();

        // Adding yourself to the playerSpace
        Player temp = new Player(playerName);
        temp.POSITION.x = WIDTH / 2;
        temp.POSITION.y = HEIGHT / 2;
        try {
            playerSpace.put(playerName, temp);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }




    public void setLayeredBoard(int width, int height, Player p) {


        //Setting players in layer 0
        gl.getContent().setBounds(0, 0, width, height);
        layeredBoard.add(gl.getContent(), JLayeredPane.DEFAULT_LAYER); // layer 0

        //Adding shop
        contentShop = new ContentShop();

        // Adding HUD
        HUD = new ContentOverlayHUD(p);
        HUD.setBounds(0, 0, 251, 40);
        HUD.setVisible(true);

        //Setting shop on layer 1
        contentShop.setBounds(width / 8, height / 8, width - (width / 4), height - (height / 4));
        layeredBoard.add(contentShop, new Integer(1));
        layeredBoard.add(HUD, new Integer(2));
        layeredBoard.moveToFront(HUD);

        gl.getContent().addShop(contentShop);
        gl.getContent().addHUD(HUD);
        super.add(layeredBoard);

        // show Jframe
        layeredBoard.setVisible(true);
        setVisible(true);
    }


}

