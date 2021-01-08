package common.src.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ZombieController {
	
	public static int wave;
	public static int numberOfZombies;
	public static Point[] spawnLocations = {new Point(50, 400), new Point(400, 50), new Point(650, 400), new Point(400, 650), new Point(50,50), new Point(50,650), new Point(650,50), new Point(650,650)};
	public static ArrayList<Zombie> zombies;
	

	public ZombieController() {
		numberOfZombies = 0;
		wave = 0;
		zombies = new ArrayList<Zombie>();
		
		new Thread(new WaveController()).start();
	}
	
	public static void moveZombies() {
		
		// have zombies move towards closest player (really simple AI)
		// remmeber to update direction for animation
		
	}

	public static void spawnNewZombies() {
		
		//Possible change number of zombies to increase difficulty
		numberOfZombies = wave;
		
		for(int i = 0; i < numberOfZombies; i++) {
			Random rand = new Random();
			int r = rand.nextInt(spawnLocations.length);
			Zombie z = new Zombie(spawnLocations[r]);
			//Zombie z = new Zombie(new Point(100,100));
			zombies.add(z);
		}
		
		System.out.println("New zombies added");
	}

	public static void checkState() {
		// If all dead, stop wave and spawn next round

		for(Zombie z : zombies) {
			if(z.isDead()) {
				zombies.remove(z);
				numberOfZombies--;
				break;
			}
		}
	}
	
}



class WaveController implements Runnable{

	@Override
	public void run() {
		System.out.println("Thread started");
		while(true) {
			while(ZombieController.numberOfZombies > 0) {
				ZombieController.moveZombies();
				ZombieController.checkState();
			}
			
			// Wait 5 seconds before next round
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ZombieController.wave++;
			ZombieController.spawnNewZombies();
		}
	}

}
