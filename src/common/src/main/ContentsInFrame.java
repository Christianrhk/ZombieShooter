package common.src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContentsInFrame extends JPanel implements KeyListener, ActionListener{

	Player p;

    public ContentsInFrame(Player p){
        super.setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
        Timer t = new Timer(4, this);
        t.start();
        this.p = p;
    }

    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	
        //using graphics 2d to draw
        Graphics2D g2d = (Graphics2D) g;

        //drawing player
        g2d.fillRect(p.getX(),p.getY(),10,10);
    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key typed!");
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		System.out.println("Key Pressed!");
		// Switch on pressed keys
		switch (keyCode) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			// Movement upwards
			System.out.println("Up pressed");
			p.moveUp();
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			// Movement downwards
			System.out.println("Down pressed");
			p.moveDown();
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			// Movement left
			System.out.println("Left pressed");
			p.moveLeft();
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			// Movement right
			System.out.println("Right pressed");
			p.moveRight();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Key Released!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}




}
