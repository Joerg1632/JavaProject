package org.nsu.controller;
import org.nsu.model.*;

import java.util.ArrayList;

public class GameController {
    private final SnakeGameModel model;

    public GameController(SnakeGameModel model) {
        this.model = model;
    }

    public void move() {
        handleFoodCollision();
        moveSnakeBody();
        moveSnakeHead();
        checkGameOver();
    }

    private void handleFoodCollision() {
        if (collision(model.getSnakeHead(), model.getFood())) {
            model.getSnakeBody().add(new Tile(model.getFood().x, model.getFood().y));
            model.food.placeFood();
        }
    }

    public void setCurrentHighScore(int highScore) {
        model.currentHighScore = highScore;
    }

    private void moveSnakeBody() {
        ArrayList<Tile> snakeBody = model.getSnakeBody();
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = model.getSnakeHead().x;
                snakePart.y = model.getSnakeHead().y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
    }

    private void moveSnakeHead() {
        model.getSnakeHead().x += model.getVelocityX();
        model.getSnakeHead().y += model.getVelocityY();
    }

    private void checkGameOver() {
        ArrayList<Tile> snakeBody = model.getSnakeBody();
        Tile snakeHead = model.getSnakeHead();

        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                model.gameOver = true;
                return;
            }
        }

        if (snakeHead.x * SnakeGameModel.getTileSize() < 0 || snakeHead.x * SnakeGameModel.getTileSize() > model.getBoardWidth() ||
                snakeHead.y * SnakeGameModel.getTileSize() < 0 || snakeHead.y * SnakeGameModel.getTileSize() > model.getBoardHeight()) {
            model.gameOver = true;
        }
    }

    public void setVelocityX(int velocityX) {
        model.snake.setVelocityX(velocityX);
    }

    public void setVelocityY(int velocityY) {
        model.snake.setVelocityY(velocityY);
    }

    private boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

}
