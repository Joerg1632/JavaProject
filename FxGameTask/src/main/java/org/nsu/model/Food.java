package org.nsu.model;

import java.util.Random;

public class Food {
    private final Random random = new Random();
    private Tile food;
    private final GameBoard gameBoard;

    public Food(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        placeFood();
    }

    public Tile getFood() {
        return food;
    }

    public void placeFood() {
        food = new Tile(random.nextInt(gameBoard.getBoardWidth() / Tile.tileSize),
                random.nextInt(gameBoard.getBoardHeight() / Tile.tileSize));
    }
}
