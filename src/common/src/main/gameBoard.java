package common.src.main;

import javax.swing.*;
import java.awt.*;

public class gameBoard extends JFrame {

    public gameBoard(int width, int height){
        setSize(width,height );
        setTitle("Zombie Shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setting layout to have a grid of widthxheight
        setLayout(new GridLayout(height,width));




        setVisible(true);


    }

    public void insertPlayer(){


    }

}
