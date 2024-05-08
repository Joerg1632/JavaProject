package org.nsu.model;

public class Tile {
    public static int tileSize = 25;
    public int x;
    public int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getTileSize() {
        return tileSize;
    }
}
