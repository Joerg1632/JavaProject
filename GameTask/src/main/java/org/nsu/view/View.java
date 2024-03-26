package org.nsu.view;

import javax.swing.*;

public class View {
    private JFrame frame;
    private JLabel titleLabel;
    private JButton startButton;

    public View() {
        frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        titleLabel = new JLabel("Морской бой");
        startButton = new JButton("Начать игру");

        JPanel panel = new JPanel();
        panel.add(titleLabel);
        panel.add(startButton);
        frame.add(panel);


    }

    public void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JButton getStartButton() {
        return startButton;
    }
}