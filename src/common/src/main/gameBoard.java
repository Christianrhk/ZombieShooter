package common.src.main;

import javax.swing.*;
import java.awt.*;

public class gameBoard extends JFrame {




    public gameBoard(int width, int height){

        // set jframe size etc.
        super.setSize(width,height );
        super.setTitle("Zombie Shooter");
        super.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adding content
        super.add(new ContentsInFrame());

        //show Jframe
        setVisible(true);


    }

    public static void main(String[] args){
        new gameBoard(800,800);


    }

}
