package org.nsu.model;

import java.util.ArrayList;

public class SnakeGameModel {
    public int currentHighScore;

    public Snake snake;
    public final Food food;
    public static Tile tile;
    public GameBoard gameBoard;
    public boolean gameOver = false;

    public SnakeGameModel() {
        snake = new Snake();
        gameBoard = new GameBoard();
        tile = new Tile(0,0);
        food = new Food(gameBoard);
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

    public static int getTileSize() {
        return tile.getTileSize();
    }

    public int getBoardWidth() {
        return gameBoard.getBoardWidth();
    }

    public int getBoardHeight() {
        return gameBoard.getBoardHeight();
    }

    public int getCurrentHighScore() {
        return currentHighScore;
    }

    public void restartGame() {
        snake = new Snake();
        food.placeFood();
        gameOver = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
