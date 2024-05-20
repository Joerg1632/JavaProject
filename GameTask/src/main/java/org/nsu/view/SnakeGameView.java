package org.nsu.view;

import org.nsu.model.SnakeGameModel;
import org.nsu.model.Tile;
import javax.swing.*;
import java.awt.*;

public class SnakeGameView extends JPanel {
    private final SnakeGameModel model;

    public SnakeGameView(SnakeGameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(model.getBoardWidth(), model.getBoardHeight()));
        setBackground(Color.GREEN);
    }

    public static void initializeGui(SnakeGameView view) {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Grid Lines
        for (int i = 0; i < model.getBoardWidth() / SnakeGameModel.getTileSize(); i++) {
            g.drawLine(i * SnakeGameModel.getTileSize(), 0, i * SnakeGameModel.getTileSize(), model.getBoardHeight());
            g.drawLine(0, i * SnakeGameModel.getTileSize(), model.getBoardWidth(), i * SnakeGameModel.getTileSize());
        }

        // Food
        g.setColor(Color.orange);
        g.fill3DRect(model.getFood().x * SnakeGameModel.getTileSize(), model.getFood().y * SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize(), true);

        // Snake Head
        g.setColor(Color.black);
        g.fill3DRect(model.getSnakeHead().x * SnakeGameModel.getTileSize(), model.getSnakeHead().y * SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize(), true);

        // Snake Body
        for (int i = 0; i < model.getSnakeBody().size(); i++) {
            Tile snakePart = model.getSnakeBody().get(i);
            g.fill3DRect(snakePart.x * SnakeGameModel.getTileSize(), snakePart.y * SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize(), true);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (model.isGameOver()) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(model.getSnakeBody().size()), SnakeGameModel.getTileSize() - 16, SnakeGameModel.getTileSize());
        } else {
            g.drawString("Score: " + String.valueOf(model.getSnakeBody().size()), SnakeGameModel.getTileSize() - 16, SnakeGameModel.getTileSize());
        }

        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("High Score: " + model.getCurrentHighScore(), model.getBoardWidth() - 120, 20);
    }
}
