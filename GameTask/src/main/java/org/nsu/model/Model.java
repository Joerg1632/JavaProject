package org.nsu.model;

public class Model {
    private int[][] playerBoard;
    private int[][] computerBoard;
    private int playerScore;
    private int computerScore;

    public Model() {
        playerBoard = new int[10][10];
        computerBoard = new int[10][10];
        playerScore = 0;
        computerScore = 0;
    }

}
