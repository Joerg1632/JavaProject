package org.nsu.controller;

import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class InputManager {
    private final SnakeGameModel model;
    private final SnakeGameView view;
    private final PropertyManager propertyManager;

    public InputManager(SnakeGameModel model, SnakeGameView view, PropertyManager propertyManager) {
        this.model = model;
        this.view = view;
        this.propertyManager = propertyManager;
    }

    public void registerKeyListeners() {
        view.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP && model.getVelocityY() != 1) {
                    model.setVelocityX(0);
                    model.setVelocityY(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && model.getVelocityY() != -1) {
                    model.setVelocityX(0);
                    model.setVelocityY(1);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && model.getVelocityX() != 1) {
                    model.setVelocityX(-1);
                    model.setVelocityY(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && model.getVelocityX() != -1) {
                    model.setVelocityX(1);
                    model.setVelocityY(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && model.isGameOver()) {
                    model.restartGame(); // Вот этот вызов метода перезапуска игры
                    view.repaint(); // Перерисовываем экран после перезапуска игры
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
