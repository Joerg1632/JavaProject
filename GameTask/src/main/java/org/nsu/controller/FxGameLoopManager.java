package org.nsu.controller;

import javafx.animation.AnimationTimer;
import org.nsu.model.SnakeGameModel;
import org.nsu.view.FxSnakeGameView;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class FxGameLoopManager {
    private final GameController controller;
    private final FxSnakeGameView view;
    private final PropertyManager propertyManager;
    private final SnakeGameModel model;
    private AnimationTimer gameLoop;
    private final PauseTransition pauseTransition;

    public FxGameLoopManager(SnakeGameModel model, FxSnakeGameView view, GameController controller) {
        this.controller = controller;
        this.view = view;
        this.model = model;
        this.propertyManager = new PropertyManager(model, controller);

        pauseTransition = new PauseTransition(Duration.millis(100));
        pauseTransition.setOnFinished(event -> updateGame());
    }

    public void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                pauseTransition.play();
            }
        };
        gameLoop.start();
    }

    public void restartGameLoop() {
        gameLoop.start();
    }

    private void updateGame() {
        controller.move();
        view.update();
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

