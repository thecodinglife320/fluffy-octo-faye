package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Fluffy Octo Faye");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        gamePanel.startGameThread();

        //display at center of screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }
}
