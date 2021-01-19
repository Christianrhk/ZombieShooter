package common.src.main;

import org.jspace.Space;

public class GameLoop implements Runnable {

	final int TICKS_PER_SECOND = 50;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 10;
	final long startingTick;
	boolean playing;

	ContentsInFrame content;

	public GameLoop(Player player, Space playerSpace, Space zombieSpace, boolean host, long startTick) {
		content = new ContentsInFrame(player, playerSpace, zombieSpace, host);
		this.startingTick = startTick;
		this.playing = true;
	}

	@Override
	public void run() {
		double next_game_tick;
		if (startingTick > System.currentTimeMillis()) {
			next_game_tick = startingTick;
		} else {
			next_game_tick = System.currentTimeMillis();
		}
		System.out.println(next_game_tick);
		int loops;

		while (playing) {
			loops = 0;
			long time = System.currentTimeMillis();
			while (time > next_game_tick && loops < MAX_FRAMESKIP) {

				
				content.updateGame();

				next_game_tick += SKIP_TICKS;
				System.out.println("Next game tick: " + next_game_tick);
				loops++;
			}


			if (content.shopVisible) {
				content.shop.repaint();
			}
			// update jFrame
			content.repaint();
		}
	}

	public ContentsInFrame getContent() {
		return content;
	}

}