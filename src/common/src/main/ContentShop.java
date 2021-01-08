package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class ContentShop extends JPanel implements ActionListener{

	private boolean bHasBeenPressed;

	public ContentShop() {
		super.setDoubleBuffered(true);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		bHasBeenPressed = false;
		
		//Create border:
		Border windowsBorder = BorderFactory.createLineBorder(Color.black, 10);
		super.setBorder(BorderFactory.createTitledBorder(windowsBorder, "Shop", TitledBorder.CENTER, TitledBorder.TOP));
		//JButton button = new JButton("Show message");
		//super.add(button);
		
		setupShop();



	}

	private void setupShop() {
		
		
		//Sets up all the labels needed
		int n = 3;
		int numberOfItems = n*n;
		super.setLayout(new GridLayout(n, n, 10, 10));
		
		for(int i=0;i<numberOfItems-1;i++ ) {
			JPanel itemPanel = createItemPanel(i+"");
			super.add(itemPanel, new Integer(i));
		}
		//super.add(createItemPanel("test"));
		/*
		
		JLabel label0 = createColoredLabel("0", Color.green);
		JLabel label1 = createColoredLabel("1", Color.green);
		JLabel label2 = createColoredLabel("2", Color.green);
		JLabel label3 = createColoredLabel("3", Color.green);
		JLabel label4 = createColoredLabel("4", Color.green);
		super.setLayout(new GridLayout(n, n));
		super.add(label0, new Integer(0));
		super.add(label1, new Integer(1));
		super.add(label2, new Integer(2));
		super.add(label3, new Integer(3));
		super.add(label4, new Integer(4));
		*/

		JButton b = new JButton("Test");
		super.add(b);
		System.out.println("Has set Grid");
	}

	private JLabel createColoredLabel(String text, Color color) {
		JLabel label = new JLabel(text);
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(color);
		label.setForeground(Color.black);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setPreferredSize(new Dimension(50, 50));
		return label;
	}
	
	private JPanel createItemPanel(String name) {
		JPanel itemPanel = new JPanel();
		itemPanel.setOpaque(true);
		itemPanel.setBackground(Color.cyan);
		itemPanel.setForeground(Color.black);
		itemPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		itemPanel.setPreferredSize(new Dimension(100, 100));
		
		JButton itemButton = new JButton(name);

		try {
			Image img = ImageIO.read(new File("src/images/clown.png"));
			
			Icon icon = new ImageIcon(img);
			itemButton.setIcon(icon);
			//Image img = ImageIO.read(new File("src/images/clown.png"));
			//itemButton.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Image for button could not be located!\n");
			e.printStackTrace();
		}
		
		itemButton.setPreferredSize(new Dimension(100,100));
		itemButton.setToolTipText("Guy in the White House lmao");
		itemButton.setActionCommand("Bought");
		
		itemPanel.setLayout(new GridBagLayout());
		itemPanel.add(itemButton);
		
		return itemPanel;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
