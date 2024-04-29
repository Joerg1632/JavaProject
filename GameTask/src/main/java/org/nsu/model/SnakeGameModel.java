package org.nsu.model;

import java.util.ArrayList;

public class SnakeGameModel {
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 600;
    public static int tileSize = 25;

    private final Snake snake;
    private final Food food;
    private final GameBoard gameBoard;
    private final GameLogic gameLogic;

    public SnakeGameModel() {
        snake = new Snake();
        food = new Food();
        gameBoard = new GameBoard();
        gameLogic = new GameLogic(snake, food, gameBoard);
    }

    public void restartGame() {
        snake.getSnakeBody().clear();
        food.placeFood();
        snake.setVelocityX(1);
        snake.setVelocityY(0);
        gameLogic.gameOver = false;
    }

    public void move() {
        gameLogic.move();
    }

    public boolean isGameOver() {
        return gameLogic.isGameOver();
    }

    public ArrayList<Tile> getSnakeBody() {
        return snake.getSnakeBody();
    }

    public Tile getSnakeHead() {
        return snake.getSnakeHead();
    }

    public Tile getFood() {
        return food.getFood();
    }

    public int getVelocityX() {
        return snake.getVelocityX();
    }

    public int getVelocityY() {
        return snake.getVelocityY();
    }

    public void setVelocityX(int velocityX) {
        snake.setVelocityX(velocityX);
    }

    public void setVelocityY(int velocityY) {
        snake.setVelocityY(velocityY);
    }
}
