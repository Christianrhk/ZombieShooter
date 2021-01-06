package common.src.main;

import org.jspace.*;

import java.io.IOException;
import java.net.UnknownHostException;

public class App {

    public static void main(String[] argv) throws InterruptedException {
        Space inbox = new RandomSpace();


        inbox.put("Hello World, marcus er en saftevands kande");

        Object[] tuple = inbox.get(new FormalField(String.class));
        System.out.println(tuple[0]);


    }

    /*

   Singleplayer mode

    */
    public static void singlePlayer(){




    }


    /*

    Multiplayer - host game.

     */
    public static void hostGame() {
        //we should be able to set port and ip in gui
        int port = 8080;

        String host = "localhost";
        String uri = "tcp://localhost:" + port + "/?conn";
        SpaceRepository repository = new SpaceRepository();

        // peer to peer communication
        repository.addGate(uri);

        //creating spaces
        Space player = new SequentialSpace();
        Space zombies = new SequentialSpace();

        //adding spaces to repository
        repository.add("player", player);
        repository.add("zombies", zombies);

        while (true) {


        }
    }


    /*

   Multiplayer - connect to game

    */
    public static void connectToGame() {
        // we should be able to set port and ip in gui
        int port = 8080;
        String host = "localhost";

        // peer to peer communication
        String uriPlayer = "tcp://" + host + ":" + port + "/player?conn";
        String uriZombies = "tcp://" + host + ":" + port + "/zombies?conn";

        try {
            Space player = new RemoteSpace(uriPlayer);
            Space zombies = new RemoteSpace(uriZombies);

        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }

        while (true) {


        }


    }

}