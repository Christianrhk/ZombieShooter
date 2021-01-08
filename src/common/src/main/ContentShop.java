package common.src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContentShop extends JPanel {


    public ContentShop(){
        super.setDoubleBuffered(true);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);

        setupShop();

        JButton button = new JButton("Show message");
        super.add(button);

    }

    private void setupShop(){
        super.setLayout(new GridLayout(10,10));
        System.out.println("Has set Grid");

    }

}
