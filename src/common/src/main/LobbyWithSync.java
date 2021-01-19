package common.src.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;

public class LobbyWithSync {

	public static String name, host;
	public static int port;
	public static JFrame frame;
	public volatile static JLabel currentConnected, currentHost, portLabel, hostLabel, nameLabel;
	public volatile static JTextField portText, hostText, nameText;
	public static JButton hostButton, joinButton, startButton;

	public static Space initSpace;

	public static boolean started, isHost, joined;

	public static List<Long> RTTs;
	
	public static long startDelay;

	public static void startLobby() {
		initGUI();

		RTTs = new ArrayList<Long>();
		startDelay = 1000;

		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFromTextFields();
				if (name.equals("")) {
					JOptionPane.showMessageDialog(frame, "You must enter a name");
					return;
				}

				initSpace = App.initHostGame(port, host);
				startButton.setEnabled(true);
				joinButton.setEnabled(false);
				isHost = true;
				System.out.println("Hosted");

				try {
					initSpace.put("Host", name);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				UpdateCheck update = new UpdateCheck(initSpace);
				new Thread(update).start();

			}
		});

		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFromTextFields();
				if (name.equals("")) {
					JOptionPane.showMessageDialog(frame, "You must enter a name");
					return;
				}

				try {
					initSpace = App.initJoinGame(port, host);
				} catch (IOException e1) {
					System.out.println("Couldn't connect to host");
					JOptionPane.showMessageDialog(frame, "Couldn't connect to host", "Error",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				hostButton.setEnabled(false);

				System.out.println("Joined");

				try {
					initSpace.put("Joined", name);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				UpdateCheck update = new UpdateCheck(initSpace);
				new Thread(update).start();
			}
		});

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					started = true;
					initSpace.put("START");
					long startTime = System.currentTimeMillis() + startDelay;
					
					System.out.println("AVG RTT: " + avgRTT());
					
					App.hostGame(port, name, startTime);
					MainMenu.disposeOfFrame();
					
					frame.dispose();
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}


	public static void getFromTextFields() {
		name = nameText.getText();
		host = hostText.getText();
		port = Integer.parseInt(portText.getText());
	}

	public static void initGUI() {

		started = false;

		frame = new JFrame("Multiplayer lobby");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(300, 280);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setAlwaysOnTop(true);
		frame.setResizable(false);

		JPanel panel = new JPanel();
		frame.add(panel);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				MainMenu.multiplayerLobbyOpen = false;
			}
		});

		panel.setLayout(null);

		portLabel = new JLabel("Port");
		portLabel.setBounds(10, 20, 80, 25);
		panel.add(portLabel);

		portText = new JTextField(20);
		portText.setText("8080");
		portText.setBounds(100, 20, 165, 25);
		panel.add(portText);

		hostLabel = new JLabel("Host");
		hostLabel.setBounds(10, 50, 80, 25);
		panel.add(hostLabel);

		hostText = new JTextField(20);
		hostText.setText("localhost");
		hostText.setBounds(100, 50, 165, 25);
		panel.add(hostText);

		nameLabel = new JLabel("Name");
		nameLabel.setBounds(10, 80, 80, 25);
		panel.add(nameLabel);

		nameText = new JTextField(20);
		nameText.setBounds(100, 80, 165, 25);
		panel.add(nameText);

		JLabel currHostLabel = new JLabel("Current host:");
		currHostLabel.setBounds(10, 140, 80, 25);
		panel.add(currHostLabel);

		currentHost = new JLabel("");
		currentHost.setBounds(100, 140, 165, 25);
		panel.add(currentHost);

		JLabel joinedLabel = new JLabel("Others joined:");
		joinedLabel.setBounds(10, 170, 80, 25);
		panel.add(joinedLabel);

		currentConnected = new JLabel("");
		currentConnected.setBounds(100, 170, 165, 25);
		panel.add(currentConnected);

		hostButton = new JButton("Host");
		hostButton.setBounds(10, 110, 80, 25);
		panel.add(hostButton);

		joinButton = new JButton("Join");
		joinButton.setBounds(100, 110, 165, 25);
		panel.add(joinButton);

		startButton = new JButton("Start");
		startButton.setEnabled(false);
		startButton.setBounds(10, 200, 265, 25);
		panel.add(startButton);

		frame.setVisible(true);

	}

	public static void runRTTcheck() {

		// Calculate round trip time
		long t0 = System.currentTimeMillis();
		try {
			initSpace.put("RTTCHECK");
			initSpace.get(new ActualField("RTTACK"));
			long t1 = System.currentTimeMillis();
			long RTT = t1 - t0;
			RTTs.add(RTT);
			initSpace.put("RTTVALUE", RTT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static long avgRTT() {

		if (RTTs.size() > 0) {
			Long total = (long) 0;
			for (Long rtt : RTTs) {
				total += rtt;
			}
			return total / RTTs.size();
		} else {
			return 0;
		}
	}

}

class UpdateCheck implements Runnable {

	Space initSpace;

	public UpdateCheck(Space s) {
		initSpace = s;
	}

	@Override
	public void run() {
		List<Object[]> list = null;
		while (!LobbyWithSync.started) {

			try {
				// Update host text
				String hostText = "";
				list = initSpace.queryAll(new ActualField("Host"), new FormalField(String.class));
				for (Object[] o : list) {
					hostText += o[1] + " ";
				}
				LobbyWithSync.currentHost.setText(hostText);

				// Update joined text
				String connectedText = "";
				list = initSpace.queryAll(new ActualField("Joined"), new FormalField(String.class));
				for (Object[] o : list) {
					LobbyWithSync.joined = true;
					connectedText += o[1] + " ";
				}
				LobbyWithSync.currentConnected.setText(connectedText);

				// Calculate RTT
				if (LobbyWithSync.isHost && LobbyWithSync.joined) {
					LobbyWithSync.runRTTcheck();
				} else if (!LobbyWithSync.isHost && LobbyWithSync.joined) {
					initSpace.get(new ActualField("RTTCHECK"));
					initSpace.put("RTTACK");
					Object[] rtt = initSpace.get(new ActualField("RTTVALUE"), new FormalField(Long.class));
					LobbyWithSync.RTTs.add((Long) rtt[1]);
				}
			

				// Check for start event
				list = initSpace.queryAll(new ActualField("START"));
				for(Object[] o : list) {
					if(!LobbyWithSync.isHost && o[0].equals("START")) {
						LobbyWithSync.started = true;
						
						long RTT = LobbyWithSync.avgRTT();
						System.out.println("AVG RTT: " + RTT);
						long startTime = (System.currentTimeMillis() - (RTT/2)) + LobbyWithSync.startDelay;
						
						App.connectToGame(LobbyWithSync.port, LobbyWithSync.host, LobbyWithSync.name, startTime);
						MainMenu.disposeOfFrame();
						
						LobbyWithSync.frame.dispose();
					}
				}

			} catch (InterruptedException e) {
				System.out.println("Some kind of error in thread in lobby");
				e.printStackTrace();
			}

		}
		
		System.out.println("Lobby thread finished");

	}
}
