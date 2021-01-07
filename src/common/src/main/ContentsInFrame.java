package common.src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContentsInFrame extends JPanel{



    public ContentsInFrame(){
        super.setDoubleBuffered(true);






    }


    @Override
    public void paintComponent(Graphics g){
        //using graphics 2d to draw
        Graphics2D g2d = (Graphics2D) g;

        //drawing player
        g2d.fillRect(player.x,player.y,10,10);

    }



}
