

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GamePanel gPanel = new GamePanel();
        JFrame frame = new JFrame();
        frame.setLocation(300,200);
        frame.setSize(700,600);
        frame.setTitle("Game");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     

        frame.add(gPanel);
        frame.setVisible(true);




    }
}