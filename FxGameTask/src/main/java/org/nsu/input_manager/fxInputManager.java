package org.nsu.input_manager;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.nsu.controller.GameController;
import org.nsu.controller.FxGameLoopManager;
import org.nsu.model.SnakeGameModel;

public class fxInputManager {
    private final GameController controller;
    private final SnakeGameModel model;
    private final FxGameLoopManager gameLoopManager;

    public fxInputManager(SnakeGameModel model, Scene scene, GameController controller, FxGameLoopManager gameLoopManager) {
        this.controller = controller;
        this.model = model;
        this.gameLoopManager = gameLoopManager;

        if (scene != null) {
            scene.setOnKeyPressed(event -> handleKeyPressed(event.getCode()));
        }
    }

    public void handleKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.UP && model.getVelocityY() != 1) {
            controller.setVelocityX(0);
            controller.setVelocityY(-1);
        } else if (keyCode == KeyCode.DOWN && model.getVelocityY() != -1) {
            controller.setVelocityX(0);
            controller.setVelocityY(1);
        } else if (keyCode == KeyCode.LEFT && model.getVelocityX() != 1) {
            controller.setVelocityX(-1);
            controller.setVelocityY(0);
        } else if (keyCode == KeyCode.RIGHT && model.getVelocityX() != -1) {
            controller.setVelocityX(1);
            controller.setVelocityY(0);
        } else if (keyCode == KeyCode.SPACE && model.isGameOver()) {
            model.restartGame();
            controller.setVelocityX(1);
            controller.setVelocityY(0);
            gameLoopManager.restartGameLoop();
        } else if (keyCode == KeyCode.ESCAPE) {
            Platform.exit();
        }
    }
}
