package common.src.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;
import org.jspace.SpaceRepository;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class StartScreen {

    public static String name, host;
    public static int port;

    public volatile static boolean started;

    public volatile static JLabel currHOST;
    public volatile static JLabel currJoined;
    public static Space hostSpace, joinSpace;

    public static void main(String[] args) throws IOException {
        started = false;
        System.out.println("Started Game");

        JFrame frame = new JFrame("Zombie Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 350);

        frame.setResizable(false);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        JLabel portLabel = new JLabel("Port");
        portLabel.setBounds(10, 20, 80, 25);
        panel.add(portLabel);

        JTextField portText = new JTextField(20);
        portText.setText("8080");
        portText.setBounds(100, 20, 165, 25);
        panel.add(portText);

        JLabel hostLabel = new JLabel("Host");
        hostLabel.setBounds(10, 50, 80, 25);
        panel.add(hostLabel);

        JTextField hostText = new JTextField(20);
        hostText.setText("localhost");
        hostText.setBounds(100, 50, 165, 25);
        panel.add(hostText);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 80, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 80, 165, 25);
        panel.add(nameText);

        JLabel currHostLabel = new JLabel("Current host:");
        currHostLabel.setBounds(10, 140, 80, 25);
        panel.add(currHostLabel);

        currHOST = new JLabel("");
        currHOST.setBounds(100, 140, 165, 25);
        panel.add(currHOST);

        JLabel joinedLabel = new JLabel("Others joined:");
        joinedLabel.setBounds(10, 170, 80, 25);
        panel.add(joinedLabel);

        currJoined = new JLabel("");
        currJoined.setBounds(100, 170, 165, 25);
        panel.add(currJoined);

        JButton hostButton = new JButton("Host");
        hostButton.setBounds(10, 110, 80, 25);
        panel.add(hostButton);
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                port = Integer.parseInt(portText.getText());
                name = nameText.getText();
                if (name.matches("")){
                    currJoined.setText("No name has been set");
                    return;
                }

                System.out.println("Host pressed");

                SpaceRepository rep = App.initHostGame(port, name);
                hostSpace = rep.get("init");

                System.out.println("Got space");
                try {
                    //hostSpace.put("NAME", name);
                    hostSpace.put("HOST", name);
                } catch (InterruptedException e1) {
                }
                currHOST.setText(name);
                //System.out.println("Got here");

                new Thread(new UpdateChecker(name, hostSpace)).start();

               //System.out.println("We never get here!");

            }
        });

        JButton joinButton = new JButton("Join");
        joinButton.setBounds(100, 110, 165, 25);
        panel.add(joinButton);
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                port = Integer.parseInt(portText.getText());
                host = hostText.getText();
                name = nameText.getText();
                if (name.matches("")){
                    currJoined.setText("No name has been set");
                    return;
                }

                try {
                    joinSpace = App.initJoinGame(port, host, name);
                    joinSpace.put("NAME", name);
                    joinSpace.put("CONNECT", name);
                    currJoined.setText(name);
                } catch (InterruptedException e1) {
                    System.out.println("penis 1");
                    e1.printStackTrace();
                } catch (UnknownHostException e1) {
                    System.out.println("penis 2");
                    e1.printStackTrace();
                } catch (IOException e1) {
                    currJoined.setText("Couldn't connect to peer");
                    return;
                }
                currJoined.paintImmediately(currJoined.getVisibleRect());

                ArrayList<String> allNames = new ArrayList<String>();

                UpdateChecker update = new UpdateChecker(name, joinSpace);
                new Thread(update).start();
                while (!started) {

                }


                allNames = update.getAllNames();

                System.out.println("Done finding names");

                App.connectToGame(port, host, name, allNames);

                frame.dispose();
            }
        });

        JButton startButton = new JButton("Start");
        startButton.setBounds(10, 200, 265, 25);
        panel.add(startButton);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                started = true;
                ArrayList<String> allNames = new ArrayList<>();
                allNames = getNames(name, hostSpace,allNames);
                try {
                    hostSpace.put("START");
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                App.hostGame(port, name, allNames);


                frame.dispose();
            }
        });

        JButton singlePlayer = new JButton("Singleplayer");
        singlePlayer.setBounds(10, 230, 265, 25);
        panel.add(singlePlayer);
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.singlePlayer();
                frame.dispose();
            }
        });

        frame.setVisible(true);

    }

    public static boolean queryStart(Space init) {
        // System.out.println("CHECKING");
        Object[] list = null;
        try {
            list = init.queryp(new ActualField("START"));
            if (list != null) {
                System.out.println(list[0]);
            }
            if (list != null && list[0].equals("START")) {
                return true;
            }
            list = init.queryp(new FormalField(String.class), new FormalField(String.class));
            if (list != null && list[0].equals("CONNECT")) {
                currJoined.setText((String) list[1]);
            } else if (list[0].equals("HOST")) {
                currHOST.setText((String) list[1]);
            }


        } catch (InterruptedException e) {
        }
        return false;
    }

    public static ArrayList<String> getNames(String name, Space init,ArrayList<String> allNames ) {
        // System.out.println("GETTTING NAMES");
        List<Object[]> list = null;
        List<Object[]> host = null;
        try {
            list = init.queryAll(new ActualField("NAME"), new FormalField(String.class));
            host = init.queryAll(new ActualField("HOST"), new FormalField(String.class));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //updating joined field in startscreen
        if (list != null) {
            for (Object[] o : list) {
                if (!((String) o[1]).equals(name) && !allNames.contains( (String) o[1])) {
                    // adding all names to list
                    allNames.add((String) o[1]);
                    if (currJoined.getText().matches("")) {
                        currJoined.setText((String) o[1]);
                    }else {
                        currJoined.setText(currJoined.getText() + ", " + (String) o[1]);
                    }
                    System.out.println((String) o[1]);
                    currJoined.paintImmediately(currJoined.getVisibleRect());
                }
            }
        }


        // updating host field in startscreen
        if (host != null && currHOST.getText().matches("")){
            for (Object[] o : host){
                if (!name.matches((String) o[1])) {
                    currHOST.setText((String) o[1]);
                    allNames.add((String) o[1]);
                }

            }
            System.out.println("HOST = " + currHOST.getText());
            currHOST.paintImmediately(currJoined.getVisibleRect());
        }



        /*
        for (String s : allNames) {
            System.out.println(s);
        }*/
        return allNames;
    }
}

class UpdateChecker implements Runnable {
    String name;
    Space space;

    ArrayList<String> allNames;

    public UpdateChecker(String name, Space space) {
        this.name = name;
        this.space = space;
        allNames = new ArrayList<String>();
    }

    public ArrayList<String> getAllNames() {
        return allNames;
    }

    @Override
    public void run() {
        while (!StartScreen.started) {
            allNames = StartScreen.getNames(name, space, allNames);
            StartScreen.started = StartScreen.queryStart(space);
        }

        System.out.println("Thread has finished");

    }

}
