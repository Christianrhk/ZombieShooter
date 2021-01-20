package common.src.main;

import org.jspace.Space;

public class GameLoop implements Runnable {

	final int TICKS_PER_SECOND = 30;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 6;
	final long startingTick, RTT;
	boolean playing;

	ContentsInFrame content;

	public GameLoop(Player player, Space playerSpace, Space zombieSpace, boolean host, long startTick, long RTT) {
		content = new ContentsInFrame(player, playerSpace, zombieSpace, host);
		this.startingTick = startTick;
		this.playing = true;
		this.RTT = RTT;
	}

	@Override
	public void run() {
		double next_game_tick;
		if (startingTick > System.currentTimeMillis()) {
			next_game_tick = startingTick;
		} else {
			next_game_tick = System.currentTimeMillis();
		}
		System.out.println(next_game_tick + " current time: " + System.currentTimeMillis());
		int loops;
		
		long halfRTT = this.RTT / 2;

		while (playing) {
			loops = 0;
			long time = System.currentTimeMillis() - halfRTT;
			while (time > next_game_tick && loops < MAX_FRAMESKIP) {

				content.updateGame();
				next_game_tick += SKIP_TICKS;
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