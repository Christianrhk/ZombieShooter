package common.src.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.Space;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContentsInFrame extends JPanel implements KeyListener, ActionListener, MouseListener {

    private static final long serialVersionUID = 1L;

    boolean multiplayer = false;

    Player p;
    
    // SoundHandler
    SoundHandler sh;
    
    // Boolean playerPosChange = false;
    ArrayList<String> allNames; // Names of all other players, used for communication
    Space space;
    boolean playerPosChange[] = {false, false, false, false};
    boolean press[] = {false, false, false, false};
    Point player2;
    BufferedImage bg;
    Space zombieSpace;
    BufferedImage blood;
    ZombieGraphics ZG;

    ContentShop shop;
    ContentOverlayHUD HUD;
    boolean shopVisible = false;

    boolean host;

    // Contructor for singleplayer
    public ContentsInFrame(Player p, Space zombieSpace ) {
        initContentsInFrame(p, zombieSpace);
        multiplayer = false;

        // this.zombie = new Zombie(50,50);
    }

    // Constructor for multiplayer
    public ContentsInFrame(Player p, Space playerSpace, ArrayList<String> allNames, Space zombieSpace, boolean host) {
        initContentsInFrame(p, zombieSpace);
        this.host = host;

        player2 = new Point(p.getX(), p.getY());

        // this.zombie = new Zombie(100,100);
        this.space = playerSpace;
        this.allNames = allNames;

        multiplayer = true;
    }

    // Shared initialization for multiplayer and singleplayer
    public void initContentsInFrame(Player p, Space zombieSpace) {
        super.setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
        addMouseListener(this);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
        sh = new SoundHandler();
        starBackGroundMusic();
        ZG = new ZombieGraphics();

        this.p = p;

        // Get images
        try {
            bg = ImageIO.read(new File("src/images/zombiebanen.png"));
            blood = ImageIO.read(new File("src/images/blood.png"));
        } catch (IOException e) {
        }
        this.zombieSpace = zombieSpace;
        super.setPreferredSize(getPreferredSize());
    }
    
	public void starBackGroundMusic() {
		sh.playBackGroundMusic("src/sounds/backgroundMusic.WAV");
	}
	

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        g2d.drawImage(bg, 0, 0, this);

        // Draw zombies
        drawAllZombies(g);

        // Draw player
        g2d.drawImage(p.IMAGE, p.getX(), p.getY(), this);

        // drawing other players
        if (multiplayer) {
            g2d.setColor(new Color(255, 0, 255));
            g2d.fillRect(player2.x, player2.y, 15, 20);
        }

    }

    private void drawAllZombies(Graphics g) {
        try {
            //System.out.println("I try to print the zombies ");
            zombieSpace.get(new ActualField("token"));
            List<Object[]> zombies = zombieSpace.queryAll(new FormalField(Zombie.class));

            for (Object[] o : zombies){
                Zombie z = (Zombie) o[0];
                ZG.drawZombie(g, z);
            }

            zombieSpace.put("token");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800,800);
	}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /*
     * To be able to verify if 2 keys are clicked simultaneously. When keys are
     * released the boolean values will be set back to false.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //System.out.println("Key Pressed!");
        // Switch on pressed keys
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            // Movement upwards
            // System.out.println("Up pressed");
            press[0] = true;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            // Movement downwards
            // System.out.println("Down pressed");
            press[1] = true;
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            // Movement left
            // System.out.println("Left pressed");
            press[2] = true;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            // Movement right
            // System.out.println("Right pressed");
            press[3] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            // Movement upwards
            // System.out.println("Up released");
            press[0] = false;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            // Movement downwards
            // System.out.println("Down released");
            press[1] = false;
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            // Movement left
            // System.out.println("Left released");
            press[2] = false;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            // Movement right
            // System.out.println("Right released");
            press[3] = false;
        }

        if (keyCode == KeyEvent.VK_B && shopVisible == false) {
            shopVisible = true;
            shop.setVisible(true);
        } else if (keyCode == KeyEvent.VK_B && shopVisible) {
            shopVisible = false;
            shop.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void updateGame() {
        // Move players
        movePlayer();

        if (!multiplayer || host) {
            // Move zombies and animate
            ZombieController.moveZombies(p);
            
        }

        // send player position and get other players position
        if (multiplayer) {
            sendUpdateToOtherPlayers();
            tryToUpdateOtherPlayers();
        }
    }

    public void sendUpdateToOtherPlayers() {
        // only update if player position has changed
        if (playerPosChange[0] || playerPosChange[1] || playerPosChange[2] || playerPosChange[3]) {
            try {
                for (String name : allNames) { // send update to all players
                    space.put("PLAYERUPDATE", name, p.getX(), p.getY());
                }

            } catch (InterruptedException e) {
                System.out.println("Failed to send player position ");
                e.printStackTrace();
            }

        }
        playerPosChange[0] = playerPosChange[1] = playerPosChange[2] = playerPosChange[3] = false;
    }

    public void tryToUpdateOtherPlayers() {
        try {
            Object[] otherPosition = space.getp(new ActualField("PLAYERUPDATE"), new ActualField(p.NAME),
                    new FormalField(Integer.class), new FormalField(Integer.class));
            if (otherPosition != null) {
                // System.out.println("Got position : " + otherPosition[2] + " " +
                // otherPosition[3]);
                player2.x = (int) otherPosition[2];
                player2.y = (int) otherPosition[3];
            } else {
                // System.out.println("Position is null");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void movePlayer() {
        if (press[0])
            playerPosChange[0] = p.moveUp();
        if (press[1])
            playerPosChange[1] = p.moveDown();
        if (press[2])
            playerPosChange[2] = p.moveLeft();
        if (press[3])
            playerPosChange[3] = p.moveRight();
    }

    public void addShop(ContentShop contentShop) {
        shop = contentShop;
        shop.setVisible(false);
    }
    
    public void addHUD(ContentOverlayHUD HUD) {
    	this.HUD = HUD;
    	this.HUD.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse clicked: " + x + " " + y);
        
        sh.playSound("src/sounds/shoot.wav");
        try {
            zombieSpace.get(new ActualField("token"));
            java.util.List<Object[]> list = zombieSpace.getAll(new FormalField(Zombie.class));

            for (Object[] o : list) {
            	boolean dead = false;
                Zombie z = (Zombie) o[0];
                if (z.collision(x, y)) {
                	int damage = 10; // GET THIS FROM PLAYER WEAPON WHEN IMPLEMENTED <------
                    if(z.takeDamage(damage)) {
                    	this.p.giveMoney(2);
                    	this.HUD.updateMoney();
                    	dead = true;
                    } 
                }
                if(dead) {
                	zombieSpace.put("updateZombies");
                } else {
                	zombieSpace.put(z);
                }
            }
            zombieSpace.put("token");

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
