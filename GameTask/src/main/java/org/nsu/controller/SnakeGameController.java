package org.nsu.controller;

import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;

public class SnakeGameController {
    public SnakeGameController(SnakeGameModel model, SnakeGameView view) {
        GameLoopManager gameLoopManager = new GameLoopManager(model, view);
        InputManager inputManager = new InputManager(model, view, gameLoopManager.getPropertyManager());

        inputManager.registerKeyListeners();
        gameLoopManager.startGameLoop();
    }
}
