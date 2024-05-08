package org.nsu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nsu.controller.GameController;
import org.nsu.controller.FxGameLoopManager;
import org.nsu.input_manager.fxInputManager;
import org.nsu.model.SnakeGameModel;
import org.nsu.view.FxSnakeGameView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SnakeGameModel model = new SnakeGameModel();
        FxSnakeGameView view = new FxSnakeGameView(model);

        GameController gameController = new GameController(model);
        FxGameLoopManager gameLoopManager = new FxGameLoopManager(model, view, gameController);
        fxInputManager inputManager = new fxInputManager(model, view.getScene(), gameController, gameLoopManager);

        Scene scene = new Scene(view, model.getBoardWidth(), model.getBoardHeight());

        scene.setOnKeyPressed(event -> {
            inputManager.handleKeyPressed(event.getCode());
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);
        primaryStage.show();

        gameLoopManager.startGameLoop();
    }
}
