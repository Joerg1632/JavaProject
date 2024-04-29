package org.nsu.model;

import java.util.ArrayList;

public class GameLogic {
    private final Snake snake;
    private final Food food;
    private final GameBoard gameBoard;
    boolean gameOver;

    public GameLogic(Snake snake, Food food, GameBoard gameBoard) {
        this.snake = snake;
        this.food = food;
        this.gameBoard = gameBoard;
    }

    public void move() {
        handleFoodCollision();
        moveSnakeBody();
        moveSnakeHead();
        checkGameOver();
    }

    private void handleFoodCollision() {
        if (collision(snake.getSnakeHead(), food.getFood())) {
            snake.getSnakeBody().add(new Tile(food.getFood().x, food.getFood().y));
            food.placeFood();
        }
    }

    private void moveSnakeBody() {
        ArrayList<Tile> snakeBody = snake.getSnakeBody();
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snake.getSnakeHead().x;
                snakePart.y = snake.getSnakeHead().y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
    }

    private void moveSnakeHead() {
        snake.getSnakeHead().x += snake.getVelocityX();
        snake.getSnakeHead().y += snake.getVelocityY();
    }

    private void checkGameOver() {
        ArrayList<Tile> snakeBody = snake.getSnakeBody();
        Tile snakeHead = snake.getSnakeHead();

        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
                return;
            }
        }

        if (snakeHead.x * SnakeGameModel.tileSize < 0 || snakeHead.x * SnakeGameModel.tileSize > SnakeGameModel.BOARD_WIDTH ||
                snakeHead.y * SnakeGameModel.tileSize < 0 || snakeHead.y * SnakeGameModel.tileSize > SnakeGameModel.BOARD_HEIGHT) {
            gameOver = true;
        }
    }

    private boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
