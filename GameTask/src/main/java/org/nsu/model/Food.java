package org.nsu.model;

import java.util.Random;

public class Food {
    private final Random random = new Random();
    private Tile food;

    public Food() {
        placeFood();
    }

    public Tile getFood() {
        return food;
    }

    public void placeFood() {
        food = new Tile(random.nextInt(SnakeGameModel.BOARD_WIDTH / SnakeGameModel.tileSize),
                random.nextInt(SnakeGameModel.BOARD_HEIGHT / SnakeGameModel.tileSize));
    }
}
