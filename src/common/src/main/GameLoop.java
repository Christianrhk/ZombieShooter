package common.src.main;

import java.awt.Dimension;
import java.util.ArrayList;

import org.jspace.Space;

public class GameLoop implements Runnable{

	final int TICKS_PER_SECOND = 50;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 10;
	boolean playing;
	
	ContentsInFrame content;
	
	public GameLoop(Player p, Space zombieSpace) {
		content = new ContentsInFrame(p, zombieSpace);
		content.setPreferredSize(new Dimension(800,800));
		this.playing = true;
	}
	
	public GameLoop(Player p, Space playerSpace, ArrayList<String> allNames, Space zombieSpace, boolean host) {
		content = new ContentsInFrame(p, playerSpace, allNames, zombieSpace, host);
		content.setPreferredSize(new Dimension(800,800));
		this.playing = true;
	}
	
	@Override
	public void run() {
		double next_game_tick = System.currentTimeMillis();
		int loops;
		
		while(playing) {
			loops = 0;
			while(System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
				
				content.updateGame();
				
				next_game_tick += SKIP_TICKS;
				loops++;
			}
			
			if (content.shopVisible) {
				content.shop.repaint();
			} else {
				// update jFrame
				content.repaint();
			}
		}
		
	}
	
	public ContentsInFrame getContent() {
		return content;
	}

}
