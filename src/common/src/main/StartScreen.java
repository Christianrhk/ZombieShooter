package common.src.main;

import java.io.BufferedReader;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartScreen {

	public static void main(String[] args) throws IOException {

		System.out.println("Started Game");

		JFrame frame = new JFrame("StartScreen xd");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);

		JPanel panel = new JPanel();
		frame.add(panel);

		panel.setLayout(null);

		JLabel portLabel = new JLabel("Port");
		portLabel.setBounds(10, 20, 80, 25);
		panel.add(portLabel);

		JTextField portText = new JTextField(20);
		portText.setBounds(100, 20, 165, 25);
		panel.add(portText);

		JLabel hostLabel = new JLabel("Host");
		hostLabel.setBounds(10, 50, 80, 25);
		panel.add(hostLabel);

		JTextField hostText = new JTextField(20);
		hostText.setBounds(100, 50, 165, 25);
		panel.add(hostText);

		JButton hostButton = new JButton("Host");
		hostButton.setBounds(10, 80, 80, 25);
		panel.add(hostButton);
		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = Integer.parseInt(portText.getText());
				App.hostGame(port);
				frame.dispose();
			}
		});

		JButton joinButton = new JButton("Join");
		joinButton.setBounds(100, 80, 165, 25);
		panel.add(joinButton);
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = Integer.parseInt(portText.getText());
				String host = hostText.getText();
				App.connectToGame(port, host);
				frame.dispose();
			}
		});

		frame.setVisible(true);

	}
}
