package common.src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContentShop extends JPanel implements KeyListener {

    private  boolean bHasBeenPressed;
    public ContentShop(){
        super.setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);

        bHasBeenPressed = false;

        setupShop();

        JButton button = new JButton("Show message");
        super.add(button);

    }


    private void setupShop(){

        super.setLayout(new GridLayout(10,10));
        System.out.println("Has set Grid");



    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_B && bHasBeenPressed == false){
            System.out.println("B has been pressed");
            super.setVisible(true);
            bHasBeenPressed = true;
        }else if (keyCode == KeyEvent.VK_B){
            System.out.println("B has been pressed 2 times and shop is closing");
            super.setVisible(false);
            bHasBeenPressed = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
