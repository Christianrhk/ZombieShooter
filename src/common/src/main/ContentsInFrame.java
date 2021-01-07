package common.src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContentsInFrame extends JPanel implements KeyListener, ActionListener{

	Player p;
	boolean press[] = {false, false, false, false};

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
		//System.out.println("Key Pressed!");
		// Switch on pressed keys
		if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			// Movement upwards
			System.out.println("Up pressed");
			press[0] = true;
		}
		if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			// Movement downwards
			System.out.println("Down pressed");
			press[1] = true;
		}
		if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			// Movement left
			System.out.println("Left pressed");
			press[2] = true;
		}
		if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			// Movement right
			System.out.println("Right pressed");
			press[3] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			// Movement upwards
			System.out.println("Up released");
			press[0] = false;
		}
		if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			// Movement downwards
			System.out.println("Down released");
			press[1] = false;
		}
		if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			// Movement left
			System.out.println("Left released");
			press[2] = false;
		}
		if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			// Movement right
			System.out.println("Right released");
			press[3] = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		movePlayer();
		
		
		repaint();
	}


	public void movePlayer() {
		if(press[0])
			p.moveUp();
		if(press[1])
			p.moveDown();
		if(press[2])
			p.moveLeft();
		if(press[3])
			p.moveRight();
	}

}
