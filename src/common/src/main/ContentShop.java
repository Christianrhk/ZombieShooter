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

public class ContentShop extends JPanel implements ActionListener {

	private boolean bHasBeenPressed;

	public ContentShop() {
		super.setDoubleBuffered(true);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);

		bHasBeenPressed = false;

		// Create border:
		Border windowsBorder = BorderFactory.createLineBorder(Color.black, 10);
		super.setBorder(BorderFactory.createTitledBorder(windowsBorder, "Shop", TitledBorder.CENTER, TitledBorder.TOP));

		// Setups up the content of the shop
		setupShop();

	}

	private void setupShop() {

		// Sets up all the itemPanels needed
		int n = 3;
		int numberOfItems = n * n;
		super.setLayout(new GridLayout(n, n, 10, 10));

		for (int i = 0; i < numberOfItems; i++) {
			JPanel itemPanel = createItemPanel("Good weapon", "tons", "fast", "69", "Cuba");
			super.add(itemPanel, new Integer(i));
		}
	}

	private JPanel createItemPanel(String name, String dmg, String rof, String ammo, String range) {
		// Sets up the panel
		JPanel itemPanel = new JPanel();
		itemPanel.setOpaque(true);
		itemPanel.setBackground(Color.cyan);
		itemPanel.setForeground(Color.black);
		itemPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		itemPanel.setPreferredSize(new Dimension(100, 100));
		itemPanel.setLayout(null);

		// Sets up the item icon
		try {
			Image img = ImageIO.read(new File("src/images/redbox.png"));

			Icon icon = new ImageIcon(img);
			JLabel iconLabel = new JLabel(icon);
			iconLabel.setBounds(115, 145, 59, 32);
			itemPanel.add(iconLabel);
		} catch (IOException e) {
			System.out.println("Icon for item could not be located!\n");
			e.printStackTrace();
		}

		// Sets up the name of the item
		JLabel itemName = new JLabel("Very good weapon");
		itemName.setBounds(10, 10, 150, 25);
		itemName.setFont(new Font("TimesRoman", Font.PLAIN, 18));

		// Sets up the specifications of the item
		JTextArea itemStats = new JTextArea(
				"Dmg: \t " + dmg + " \nRate of Fire: \t " + rof + " \nAmmo: \t " + ammo + " \nRange: \t " + range, 50,
				50);
		itemStats.setBounds(10, 40, 164, 100);
		itemStats.setFont(new Font("Helvetica", Font.PLAIN, 18));

		// Sets up the buy-button for the item
		JButton buyButton = new JButton("Buy item");
		buyButton.setBounds(10, 145, 100, 32);

		// Adds the remaining objects to the JPanel
		itemPanel.add(buyButton);
		itemPanel.add(itemName);
		itemPanel.add(itemStats);

		return itemPanel;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
