package common.src.main;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
	
	Player p;
	
	public KeyListener(Player p) {
		this.p = p;
	}
	
	public void KeyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
				
		// Switch on pressed keys
		switch(keyCode) {
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				// Movement upwards
				p.moveUp();
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				// Movement downwards
				p.moveDown();
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				// Movement left
				p.moveLeft();
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				// Movement right
				p.moveRight();
				break;
		}
	}
	
	public void KeyReleased() {
		
	}
}
