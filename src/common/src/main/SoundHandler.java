package common.src.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler implements Runnable{
	
	boolean playing;
	String filePath;
	
	public SoundHandler(boolean playing, String filePath) {
		this.playing = playing;
		this.filePath = filePath;
	}

	public void playMusic() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			File soundFile = new File(filePath);

			if (soundFile.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				
				while(playing) {
					//making sure the daemon thread doesnt kill itself
					//System.out.println("playing");
				}
			} else {
				System.out.print("Music path didnt exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
