package org.nsu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nsu.controller.*;
import org.nsu.input_manager.InputManager;
import org.nsu.input_manager.fxInputManager;
import org.nsu.model.SnakeGameModel;
import org.nsu.view.*;

import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please specify either 'swing' or 'javafx' as an argument:");
        String viewType = scanner.nextLine().toLowerCase();

        switch (viewType) {
            case "swing":
                SwingUtilities.invokeLater(Main::startSwing);
                break;
            case "javafx":
                startJavaFX();
                break;
            default:
                System.out.println("Unknown argument: " + viewType);
                System.out.println("Please specify either 'swing' or 'javafx' as an argument.");
        }
    }

    private static void startSwing() {
        SnakeGameModel model = new SnakeGameModel();
        SnakeGameView view = new SnakeGameView(model);

        GameController gameController = new GameController(model);
        GameLoopManager gameLoopManager = new GameLoopManager(model, view, gameController);
        InputManager inputManager = new InputManager(model, view, gameController, gameLoopManager);

        inputManager.registerKeyListeners();
        gameLoopManager.startGameLoop();

        SnakeGameView.initializeGui(view);
    }

    private static void startJavaFX() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        new JFXPanel();
        Platform.runLater(() -> {
            future.complete(null);
        });
        future.join();
        Application.launch(JavaFxApp.class);
    }

    public static class JavaFxApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            Platform.runLater(() -> {
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
                primaryStage.toFront();

                gameLoopManager.startGameLoop();
            });
        }
    }
}
