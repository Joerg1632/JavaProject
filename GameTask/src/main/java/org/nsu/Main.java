package org.nsu;

import org.nsu.controller.SnakeGameController;
import org.nsu.model.GameBoard;
import org.nsu.model.SnakeGameModel;
import org.nsu.view.SnakeGameView;



public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        SnakeGameModel model = new SnakeGameModel();
        SnakeGameView view = new SnakeGameView(model, gameBoard);
        SnakeGameController controller = new SnasdfkeGameController(model, view);

        SnakeGameView.initializeGui(view);
    }
}
