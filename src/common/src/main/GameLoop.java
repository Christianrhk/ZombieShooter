package common.src.main;

import java.util.ArrayList;

import org.jspace.Space;

public class GameLoop implements Runnable{

	final int TICKS_PER_SECOND = 50;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 10;
	
	ContentsInFrame content;
	
	public GameLoop(Player p) {
		content = new ContentsInFrame(p);
	}
	
	public GameLoop(Player p, Space playerSpace, ArrayList<String> allNames) {
		content = new ContentsInFrame(p, playerSpace, allNames);
	}
	
	
	@Override
	public void run() {
		double next_game_tick = System.currentTimeMillis();
		int loops;
		
		while(true) {
			loops = 0;
			while(System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
				
				content.updateGame();
				
				next_game_tick += SKIP_TICKS;
				loops++;
			}
			
			System.out.println("repaint");
		}
		
	}
	
	public ContentsInFrame getContent() {
		return content;
	}

}
