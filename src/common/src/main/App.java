package common.src.main;

import org.jspace.*;

import java.awt.Component;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class App {


	public static boolean started = false;
	
	public static SpaceRepository repository;
	
    /*

   Singleplayer mode

    */
    public static void singlePlayer() {
        Space zombieSpace = new SequentialSpace();
    	ZombieController ZC = new ZombieController(zombieSpace);
    	GameBoard game = new GameBoard(800, 800,zombieSpace);
    }


    /*

    # Multiplayer - host game.
    We should be able to set port in gui
    */

    public static SpaceRepository initHostGame(int port, String name) {
    	  String host = "localhost";
          String uri = "tcp://" + host + ":" + port + "/?keep";

          repository = new SpaceRepository();
          // peer to peer communication
          repository.addGate(uri);

          //creating spaces
          Space initHost = new SequentialSpace();
          repository.add("init", initHost);
          
          
          System.out.println("THIS IS THE HOST");
          return repository;
    }
    
    public static Space initJoinGame(int port, String host, String name) throws UnknownHostException, IOException {
    	String uriInit = "tcp://" + host + ":" + port + "/init?keep";
    	Space initConnect = new RemoteSpace(uriInit);

    	
    	System.out.println("THIS IS THE JOINER");
		return initConnect;
    	
    }
    
    
    public static void hostGame(int port, String name, ArrayList<String> allNames) {
//        String host = "localhost";
//        String uri = "tcp://" + host + ":" + port + "/?keep";
//
//        SpaceRepository repository = new SpaceRepository();

        // peer to peer communication
       // repository.addGate(uri);

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

        try {
            System.out.println(Arrays.toString(zombies.getp((new FormalField(String.class)))));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Size of all names: " + allNames.size());
        ZombieController ZC = new ZombieController(zombies);
		GameBoard game = new GameBoard(800,800, player, name, allNames, zombies, true);


    }


    /*

    # Multiplayer - connect to game

    we should be able to set port and ip in gui
    */
    public static void connectToGame(int port, String host, String name, ArrayList<String> allNames) {
        // peer to peer communication
        String uriPlayer = "tcp://" + host + ":" + port + "/player?keep";
        String uriZombies = "tcp://" + host + ":" + port + "/zombies?keep";
        //String uriEnvironment = "tcp://" + host + ":" + port + "/environment?keep";
        //String uriShop = "tcp://" + host + ":" + port + "/shop?keep";
        //String uriInit = "tcp://" + host + ":" + port + "/init?conn";

            try {
                Space player = new RemoteSpace(uriPlayer);
				Space zombies = new RemoteSpace(uriZombies);

				zombies.put("This is some big anus");
				System.out.println("Size of all names: " + allNames.size());
	            GameBoard game = new GameBoard(800,800, player, name, allNames, zombies, false);
	            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

}