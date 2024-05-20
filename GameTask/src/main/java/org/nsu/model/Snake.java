package org.nsu.model;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Tile> snakeBody;
    private final Tile snakeHead;
    private int velocityX;
    private int velocityY;

    public Snake() {
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        snakeBody.add(snakeHead);
        velocityX = 1;
        velocityY = 0;
    }

    public ArrayList<Tile> getSnakeBody() {
        return snakeBody;
    }

    public Tile getSnakeHead() {
        return snakeHead;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}
