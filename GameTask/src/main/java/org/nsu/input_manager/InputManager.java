package org.nsu.input_manager;

import org.nsu.controller.GameController;
import org.nsu.controller.GameLoopManager;
import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager {
    private final GameController controller;
    private final SnakeGameView view;
    private final SnakeGameModel model;
    private final GameLoopManager gameLoopManager;

    public InputManager(SnakeGameModel model, SnakeGameView view, GameController controller, GameLoopManager gameLoopManager) {
        this.controller = controller;
        this.view = view;
        this.model = model;
        this.gameLoopManager = gameLoopManager;
    }

    public void registerKeyListeners() {
        view.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP && model.getVelocityY() != 1) {
                    controller.setVelocityX(0);
                    controller.setVelocityY(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && model.getVelocityY() != -1) {
                    controller.setVelocityX(0);
                    controller.setVelocityY(1);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && model.getVelocityX() != 1) {
                    controller.setVelocityX(-1);
                    controller.setVelocityY(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && model.getVelocityX() != -1) {
                    controller.setVelocityX(1);
                    controller.setVelocityY(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && model.isGameOver()) {
                    model.restartGame();
                    controller.setVelocityX(1);
                    controller.setVelocityY(0);
                    gameLoopManager.restartGameLoop();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}

        });
        view.setFocusable(true);
    }
}
