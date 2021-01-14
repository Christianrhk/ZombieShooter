package common.src.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

public class SoundHandler implements Runnable {

	Space soundSpace;

	public SoundHandler() {
		soundSpace = new SequentialSpace();
	}

	public void playBackGroundMusic(String filePath) {
		new Thread(new BackGroundMusicPlayer(filePath)).start();
	}

	public void playSound(String path) {
		// System.out.println("PLAY SOUND FUNCTION!");
		try {
			soundSpace.put("PLAYSOUND", path);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		System.out.println("Sound Thread Started");
		while (true) {
			//System.out.println("Going");
			Object[] o = null;
			try {
				o = soundSpace.get(new ActualField("PLAYSOUND"), new FormalField(String.class));
				
				File soundFile = new File((String) o[1]);

				if (soundFile.exists()) {
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInput);
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-15.0f);
					clip.start();
				}
			} catch (InterruptedException | UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}

class BackGroundMusicPlayer implements Runnable {
	boolean playing;
	String filePath;

	public BackGroundMusicPlayer(String filePath) {
		this.playing = true;
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
				gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
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
