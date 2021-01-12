package common.src.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContentOverlayHUD extends JPanel {
	
	JLabel HP, armor, money;

	BufferedImage heart, shield, dollar;
	
	public ContentOverlayHUD(Player p) {
		// TODO Auto-generated constructor stub
		super.setDoubleBuffered(true);
		
		//Using RGBA (Red, Green, Blue, Alpha) to set the value of the background to transparent
		super.setBackground(new Color (0,0,0,0));
		super.setLayout(null);
		

		// Get images
        try {
            heart = ImageIO.read(new File("src/images/heart.png"));
            shield = ImageIO.read(new File("src/images/shield.png"));
            dollar = ImageIO.read(new File("src/images/money.png"));
        } catch (IOException e) {
        	System.out.println("Couldnt load one or more image icons for HUD");
        }
		
        Icon heartIcon = new ImageIcon(heart);
		JLabel heartLabel = new JLabel(heartIcon);
		heartLabel.setBounds(5, 5, 32, 32);
		
		this.HP = new JLabel(String.valueOf(p.getHP()));
		this.HP.setForeground(Color.RED);
		this.HP.setBounds(37, 5, 50, 32);
		
		Icon armorIcon = new ImageIcon(shield);
		JLabel armorLabel = new JLabel(armorIcon);
		armorLabel.setBounds(87, 5, 32, 32);
		
		this.armor = new JLabel(String.valueOf(p.getArmor()));
		this.armor.setForeground(Color.YELLOW);
		this.armor.setBounds(119, 5, 50, 32);
		
		Icon moneyIcon = new ImageIcon(dollar);
		JLabel moneyLabel = new JLabel(moneyIcon);
		moneyLabel.setBounds(169, 5, 32, 32);
		
		this.money = new JLabel(String.valueOf(p.getMoney()));
		this.money.setForeground(Color.GREEN);
		this.money.setBounds(201, 5, 50, 32);
		
		
		super.add(heartLabel);
		super.add(HP);
		super.add(armorLabel);
		super.add(armor);
		super.add(moneyLabel);
		super.add(money);
	}
	
	public void updateHP(Player p) {
		this.HP.setText(String.valueOf(p.getHP()));
	}
	
	public void updateArmor(Player p) {
		this.armor.setText(String.valueOf(p.getArmor()));
	}
	
	public void updateMoney(Player p) {
		this.money.setText(String.valueOf(p.getMoney()));
	}
	
}
