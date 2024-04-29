package org.nsu.view;

import org.nsu.model.GameBoard;
import org.nsu.model.SnakeGameModel;
import org.nsu.model.Tile;
import javax.swing.*;
import java.awt.*;

public class SnakeGameView extends JPanel {
    private final SnakeGameModel model;
    private final GameBoard gameBoard;
    private int currentHighScore;

    public SnakeGameView(SnakeGameModel model, GameBoard gameBoard) {
        this.model = model;
        this.gameBoard = gameBoard;
        setPreferredSize(new Dimension(gameBoard.getBoardWidth(), gameBoard.getBoardHeight()));
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

    public void setCurrentHighScore(int highScore) {
        currentHighScore = highScore;
    }

    public int getCurrentHighScore() {
        return currentHighScore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Grid Lines
        for (int i = 0; i < gameBoard.getBoardWidth() / model.tileSize; i++) {
            g.drawLine(i * model.tileSize, 0, i * model.tileSize, gameBoard.getBoardHeight());
            g.drawLine(0, i * model.tileSize, gameBoard.getBoardWidth(), i * model.tileSize);
        }

        // Food
        g.setColor(Color.black);
        g.fill3DRect(model.getFood().x * model.tileSize, model.getFood().y * model.tileSize, model.tileSize, model.tileSize, true);

        // Snake Head
        g.setColor(Color.black);
        g.fill3DRect(model.getSnakeHead().x * model.tileSize, model.getSnakeHead().y * model.tileSize, model.tileSize, model.tileSize, true);

        // Snake Body
        for (int i = 0; i < model.getSnakeBody().size(); i++) {
            Tile snakePart = model.getSnakeBody().get(i); // Заменил SnakeGameModel.Tile на просто Tile
            g.fill3DRect(snakePart.x * model.tileSize, snakePart.y * model.tileSize, model.tileSize, model.tileSize, true);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (model.isGameOver()) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(model.getSnakeBody().size()), model.tileSize - 16, model.tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(model.getSnakeBody().size()), model.tileSize - 16, model.tileSize);
        }

        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("High Score: " + getCurrentHighScore(), gameBoard.getBoardWidth() - 120, 20);
    }
}
