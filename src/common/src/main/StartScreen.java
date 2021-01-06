package common.src.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartScreen {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Started Game");
		
		System.out.println("Do you wish to host or connect to a game?");
		System.out.println("Press 1 to host, 2 to connect");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
		int option = Integer.parseInt(input.readLine());
		int port;
		// Host the game
		if(option == 1) {
			System.out.println("Enter port to run on");
			
			port = Integer.parseInt(input.readLine()); 
			App.hostGame(port);
		} else if(option == 2) { // Join a game
			System.out.println("Enter a port to join");
			port = Integer.parseInt(input.readLine()); 
			System.out.println("Enter host IP");
			String host = input.readLine();
			
			App.connectToGame(port, host);
		}

	}
}
