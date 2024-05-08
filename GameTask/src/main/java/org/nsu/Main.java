package org.nsu;

import org.nsu.controller.GameController;
import org.nsu.controller.GameLoopManager;
import org.nsu.input_manager.InputManager;
import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;

public class Main {
    public static void main(String[] args) {
        SnakeGameModel model = new SnakeGameModel();
        SnakeGameView view = new SnakeGameView(model);

        GameController gameController = new GameController(model);
        GameLoopManager gameLoopManager = new GameLoopManager(model, view, gameController);
        InputManager inputManager = new InputManager(model, view, gameController, gameLoopManager);

        inputManager.registerKeyListeners();
        gameLoopManager.startGameLoop();

        SnakeGameView.initializeGui(view);
    }
}