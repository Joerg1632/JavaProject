package org.nsu.controller;

import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoopManager implements ActionListener {
    private final Timer gameLoop;
    private final GameController controller;
    private final SnakeGameView view;
    private final PropertyManager propertyManager;
    private final SnakeGameModel model;

    public GameLoopManager(SnakeGameModel model, SnakeGameView view, GameController controller) {
        this.controller = controller;
        this.view = view;
        this.model = model;
        this.gameLoop = new Timer(100, this);
        this.propertyManager = new PropertyManager(model, controller);
    }

    public void startGameLoop() {
        this.gameLoop.start();
    }

    public void restartGameLoop() {
        this.gameLoop.restart();
    }

    public void actionPerformed(ActionEvent e) {
        controller.move();
        view.repaint();
        if (model.isGameOver()) {
            gameLoop.stop();
            int currentScore = model.getSnakeBody().size();
            if (currentScore > model.getCurrentHighScore()) {
                controller.setCurrentHighScore(currentScore);
                propertyManager.saveProperties();
            }
        }
    }
}
