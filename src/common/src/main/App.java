package common.src.main;

import org.jspace.*;

import java.io.IOException;
import java.net.UnknownHostException;

public class App {

    public static void main(String[] argv) throws InterruptedException {
        Space inbox = new RandomSpace();


        inbox.put("Hello World, marcus er en saftevands¨kande");

        Object[] tuple = inbox.get(new FormalField(String.class));
        System.out.println(tuple[0]);


    }

    /*

   Singleplayer mode

    */
    public static void singlePlayer(){




    }


    /*

    # Multiplayer - host game.
    We should be able to set port in gui
    */
    public static void hostGame(int port) {
        String host = "localhost";
        String uri = "tcp://"+ host +":" + port + "/?keep";

        SpaceRepository repository = new SpaceRepository();

        // peer to peer communication
        repository.addGate(uri);

        //creating spaces
        Space player = new SequentialSpace();
        Space zombies = new SequentialSpace();
        Space environment = new SequentialSpace();
        Space shop = new SequentialSpace();

        //adding spaces to repository
        repository.add("player", player);
        repository.add("zombies", zombies);
        repository.add("environment", environment);
        repository.add("shop", shop);

        while (true) {


        }
    }


    /*

    # Multiplayer - connect to game

    we should be able to set port and ip in gui
    */
    public static void connectToGame(int port, String host) {
        // peer to peer communication
        String uriPlayer = "tcp://" + host + ":" + port + "/player?keep";
        String uriZombies = "tcp://" + host + ":" + port + "/zombies?keep";
        String uriEnvironment  = "tcp://" + host + ":" + port + "/environment?keep";
        String uriShop  = "tcp://" + host + ":" + port + "/shop?keep";
        
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