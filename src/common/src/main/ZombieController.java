package common.src.main;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.math.*;


import common.src.main.Entity.direction;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

import static common.src.main.Zombie.type;


public class ZombieController {

	public static int wave;
	public static int numberOfZombies;

	public static Space zombieSpace;

	public ZombieGraphics ZG;
	
	public ZombieController(Space zombieSpace) {
		numberOfZombies = 0;
		wave = 0;

		ZombieController.zombieSpace = zombieSpace;
		new Thread(new WaveController()).start();
	}

	public static void moveZombies(List<Player> playerList) {
		if (playerList.size() == 0) return;

		// have zombies move towards closest player (really simple AI)
		// remmeber to update direction for animation
		try {
			zombieSpace.get(new ActualField("token"));
			List<Object[]> list = zombieSpace.getAll(new FormalField(Zombie.class));
			Player p = playerList.get(0);
			int tempDistance,  maxDistance = Integer.MAX_VALUE;
			for (Object[] o : list) {
				Zombie z = (Zombie) o[0];
				for (Player q : playerList){
					tempDistance = calculateDistance(q.getX() - z.POSITION.x,q.getY() - z.POSITION.y );
					if (maxDistance>tempDistance){
						p = q;
						maxDistance = tempDistance;
					}
				}


				int dx = p.getX() - z.POSITION.x;
				int dy = p.getY() - z.POSITION.y;

				double max = Math.max(Math.abs(dx), Math.abs(dy));

				int ddx = (int) Math.round((double) dx / max);
				int ddy = (int) Math.round((double) dy / max);

				z.POSITION.x += ddx;
				z.POSITION.y += ddy;

				if (max == Math.abs(dx)) {
					if (dx >= 0) {
						z.directionFacing = direction.RIGHT;
					} else {
						z.directionFacing = direction.LEFT;
					}
				} else {
					if (dy >= 0) {
						z.directionFacing = direction.DOWN;
					} else {
						z.directionFacing = direction.UP;
					}
				}

				zombieSpace.put(z);
			}
			zombieSpace.put("token");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static int calculateDistance(int x, int y){
		return (int) Math.sqrt(x*x+y*y);
	}


	public static void spawnNewZombies() {

		// Possible change number of zombies to increase difficulty
		numberOfZombies = wave;
		System.out.println("Wave number: " + wave);
		try {
			zombieSpace.get(new ActualField("token"));
			Random rand = new Random();
			// Spawn elite zombies
			int numberOfElites = numberOfZombies/5;
			for (int i = 0; i<numberOfElites;i++){
				System.out.println("Spawned an elite");
				Zombie z = new Zombie(getRandomSpawnLocation(rand),type.ELITE);
				zombieSpace.put(z);
			}
			// spawn normal zombies
			for (int i = 0; i < numberOfZombies-numberOfElites; i++) {
				// adding a new zombie with a random generated spawn location
				Zombie z = new Zombie(getRandomSpawnLocation(rand),type.NORMAL);
				zombieSpace.put(z);

			}

			zombieSpace.put("token");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private static Point getRandomSpawnLocation(Random ran){
		int caseRan = ran.nextInt(4);
		int[] temp = {ran.nextInt(300),ran.nextInt(300)};
		Point p = new Point();
		switch (caseRan){
			case 0:
				p.x = temp[0];
				p.y = temp[1];
				break;
			case 1:
				p.x = 800-temp[0];
				p.y = temp[1];
				break;
			case 2:
				p.x = temp[0];
				p.y = 800-temp[1];
				break;
			case 3:
				p.x = 800-temp[0];
				p.y = 800-temp[1];
				break;
		}
		return p;
	}
}

class WaveController implements Runnable {

	@Override
	public void run() {
        try {
			ZombieController.zombieSpace.put("token");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {

        	// update zombies to check id any has been shot
			while (ZombieController.numberOfZombies > 0) {
				try {
					// Only update when a zombie has been shot
					Object[] s = ZombieController.zombieSpace.get(new ActualField("updateZombies"));
					System.out.println((String) s[0]);
					ZombieController.numberOfZombies--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// Wait 5 seconds before next round
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ZombieController.wave++;
			ZombieController.spawnNewZombies();

		}
	}

}
