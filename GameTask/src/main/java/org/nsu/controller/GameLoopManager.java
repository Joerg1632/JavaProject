package org.nsu.controller;

import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameLoopManager implements ActionListener {
    private final Timer gameLoop;
    private final SnakeGameModel model;
    private final SnakeGameView view;
    private final PropertyManager propertyManager;

    public GameLoopManager(SnakeGameModel model, SnakeGameView view) {
        this.model = model;
        this.view = view;
        this.gameLoop = new Timer(100, this);
        this.propertyManager = new PropertyManager(view);
    }

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void startGameLoop() {
        this.gameLoop.start();
    }

    public void actionPerformed(ActionEvent e) {
        model.move();
        view.repaint();
        if (model.isGameOver()) {
            gameLoop.stop();
            int currentScore = model.getSnakeBody().size();
            if (currentScore > view.getCurrentHighScore()) {
                view.setCurrentHighScore(currentScore);
                propertyManager.saveProperties();
            }
        }
    }
}

