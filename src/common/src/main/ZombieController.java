package common.src.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import common.src.main.Entity.direction;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

public class ZombieController {

    public static int wave;
    public static int numberOfZombies;
    public static Point[] spawnLocations = {new Point(50, 400), new Point(400, 50), new Point(650, 400),
            new Point(400, 650), new Point(50, 50), new Point(50, 650), new Point(650, 50), new Point(650, 650)};

    public static ArrayList<Zombie> zombies;


    public static Space zombieSpace;
    static boolean host;

    public ZombieController(Space zombieSpace) {
        numberOfZombies = 0;
        wave = 0;
        zombies = new ArrayList<Zombie>();

        this.zombieSpace = zombieSpace;
        this.host = host;
        new Thread(new WaveController()).start();
    }

    public ZombieController(){

        numberOfZombies = 0;
        wave = 0;
        zombies = new ArrayList<Zombie>();

        this.zombieSpace = new SequentialSpace();
        this.host = host;
        new Thread(new WaveController()).start();

    }

    public static void moveZombies(Player p) {

        // have zombies move towards closest player (really simple AI)
        // remmeber to update direction for animation


        try {
            zombieSpace.getp(new ActualField("token"));
            List<Object[]> list  = zombieSpace.getAll(new FormalField(Zombie.class));
            zombies.clear();

            for (Object[] o : list) {
                Zombie z = (Zombie) o[0];
                zombies.add(z);
                int dx = p.getX() - z.POSITION.x;
                int dy = p.getY() - z.POSITION.y;
                // System.out.println("Zombiespawn at: " + z.POSITION.x + "," + z.POSITION.y);

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

                z.zombieRunAnimation();

                zombieSpace.put(z);
            }
            zombieSpace.put("token");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public static void spawnNewZombies() {

        // Possible change number of zombies to increase difficulty
        numberOfZombies = wave;
        try {
            zombieSpace.getp(new ActualField("token"));
            zombies.clear();
            for (int i = 0; i < numberOfZombies; i++) {
                Random rand = new Random();
                int r = rand.nextInt(spawnLocations.length);
                Point newP = new Point(spawnLocations[r]);
                Zombie z = new Zombie(newP);
                System.out.println("Zombie added at " + z.POSITION.x + ", " + z.POSITION.y);
                // Zombie z = new Zombie(new Point(100,100));
                zombieSpace.put(z);
                zombies.add(z);
            }

            zombieSpace.put("token");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("New zombies added");


    }

    public static void checkState() {
        // If all dead, stop wave and spawn next round
        try {
            zombieSpace.getp(new ActualField("token"));
            List<Object[]> zombies = zombieSpace.getAll(new FormalField(Zombie.class));


            for (Object[] z : zombies) {
                Zombie q = (Zombie) z[0];
                if (q.isDead()) {

                    numberOfZombies--;
                }else {
                    zombieSpace.put(z);

                }
            }

            zombieSpace.put("token");
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }


}

class WaveController implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread started");
        while (true) {
            while (ZombieController.numberOfZombies > 0) {
                ZombieController.checkState();
            }

            // Wait 5 seconds before next round
            try {
                System.out.println("Sleeping");
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
