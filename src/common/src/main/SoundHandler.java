package common.src.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundHandler {

	public void playBackGroundMusic(boolean playing, String filePath) {
		new Thread(new BackGroundMusicPlayer(playing, filePath)).start();
	}

	public void playSound(String filePath) {
		new Thread(new SoundSamplePlayer(filePath)).start();
	}
}

class BackGroundMusicPlayer implements Runnable {
	boolean playing;
	String filePath;

	public BackGroundMusicPlayer(boolean playing, String filePath) {
		this.playing = playing;
		this.filePath = filePath;
	}

	@Override
	public void run() {
		try {
			File soundFile = new File(filePath);

			if (soundFile.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-30.0f); // Reduce volume by 10 decibels.
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);

				while (playing) {
					// making sure the daemon thread doesnt kill itself
					// System.out.println("playing");
				}
			} else {
				System.out.print("Music path didnt exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class SoundSamplePlayer implements Runnable {
	String filePath;

	public SoundSamplePlayer(String soundPath) {
		this.filePath = soundPath;
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
				clip.loop(1);

				while (clip.getMicrosecondLength() != clip.getMicrosecondPosition()) {

				}
			} else {
				System.out.print("Music path didnt exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
