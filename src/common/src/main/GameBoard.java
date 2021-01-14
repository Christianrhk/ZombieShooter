package common.src.main;

import javax.swing.*;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

public class GameBoard extends JFrame {


    private static final long serialVersionUID = 1L;
    int WIDTH, HEIGHT;
    JLayeredPane layeredBoard;

    GameLoop gl;
    ContentShop contentShop;
    ContentOverlayHUD HUD;

    // Multiplayer  constructor
    public GameBoard(int width, int height, Space playerSpace, String playerName, Space zombieSpace, boolean host) {
        Player player = new Player(playerName);
        setGameBoard(width, height, player, playerSpace, host);

        if (host) super.setTitle("Zombie Shooter - host");
        // adding content
        gl = new GameLoop(player, playerSpace, zombieSpace, host);
        new Thread(gl).start();

        //insert shop as a layered board
        setLayeredBoard(width, height, player);


    }


    public void setGameBoard(int width, int height, Player player, Space playerSpace, boolean host) {
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

        player.POSITION.x = WIDTH / 2;
        player.POSITION.y = HEIGHT / 2;
        try {
            playerSpace.put(player.NAME, player);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }




    @SuppressWarnings("deprecation")
	public void setLayeredBoard(int width, int height, Player p) {


        //Setting players in layer 0
        gl.getContent().setBounds(0, 0, width, height);
        layeredBoard.add(gl.getContent(), JLayeredPane.DEFAULT_LAYER); // layer 0

        // Adding HUD
        HUD = new ContentOverlayHUD(p);
        HUD.setBounds(0, 0, 301, 40);
        HUD.setVisible(true);
        
      //Adding shop
        contentShop = new ContentShop(p, HUD);

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