package common.src.main;

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
import java.util.ArrayList;

public class ContentsInFrame extends JPanel implements KeyListener, ActionListener {

    boolean multiplayer = false;

    Player p;
    Boolean playerPosChange;
    ArrayList<String> allNames;
    Space space;
    boolean press[] = {false, false, false, false};
    Point player2;
    Timer t;

    // Contructor for singleplayer
    public ContentsInFrame(Player p) {
        super.setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);

        //setting timer
        t = new Timer(4, this);
        t.start();

        this.p = p;
        multiplayer = false;
    }

    //Constructor for multiplayer
    public ContentsInFrame(Player p, Space playerSpace, ArrayList<String> allNames) {
        super.setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);

        player2 = new Point(p.getX(), p.getY());

        //setting timer
        t = new Timer(4, this);
        t.start();

        // init variables
        this.p = p;
        this.space = playerSpace;
        this.allNames = allNames;

        multiplayer = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // using graphics 2d to draw
        Graphics2D g2d = (Graphics2D) g;

        // drawing player
        g2d.fillRect(p.getX(), p.getY(), 10, 10);

        // drawing other players
        if (multiplayer) {
            g2d.setColor(new Color(255, 0, 255));
            g2d.fillRect(player2.x, player2.y, 10, 10);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Key typed!");
    }

    /*
    To be able to verify if 2 keys are clicked simultaneously.
    When keys are released the boolean values will be set back to false.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // System.out.println("Key Pressed!");
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
            //System.out.println("Up released");
            press[0] = false;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            // Movement downwards
            //System.out.println("Down released");
            press[1] = false;
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            // Movement left
            //System.out.println("Left released");
            press[2] = false;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            // Movement right
            //System.out.println("Right released");
            press[3] = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // t.start();
        movePlayer();

        // send player position and get other players position
        if (multiplayer) {
            sendUpdateToOtherPlayers();
            tryToUpdateOtherPlayers();
        }

        //update jFrame
        repaint();
    }

    public void sendUpdateToOtherPlayers() {
        if (playerPosChange) { // only update if play position has changed
            try {
                for (String name : allNames) { // send update to all players
                    space.put("PLAYERUPDATE", name, p.getX(), p.getY());
                }
                playerPosChange = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tryToUpdateOtherPlayers() {
        try {
            Object[] otherPosition = space.getp(new ActualField("PLAYERUPDATE"), new ActualField(p.NAME),
                    new FormalField(Integer.class), new FormalField(Integer.class));
            if (otherPosition != null) {
                System.out.println("Got position : " + otherPosition[2] + " " + otherPosition[3]);
                player2.x = (int) otherPosition[2];
                player2.y = (int) otherPosition[3];
            } else {
                System.out.println("Position is null");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void movePlayer() {
        if (press[0])
            playerPosChange = p.moveUp();
        if (press[1])
            playerPosChange = p.moveDown();
        if (press[2])
            playerPosChange = p.moveLeft();
        if (press[3])
            playerPosChange =  p.moveRight();
    }

}
