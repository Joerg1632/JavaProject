package org.nsu.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.nsu.model.SnakeGameModel;
import org.nsu.model.Tile;
import javafx.scene.layout.Background;

public class FxSnakeGameView extends Pane {
    private final SnakeGameModel model;
    private final Rectangle[][] grid;
    private final Text scoreText;
    private final Text highScoreText;

    public FxSnakeGameView(SnakeGameModel model) {
        this.model = model;
        setPrefSize(model.getBoardWidth(), model.getBoardHeight());
        setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        grid = new Rectangle[model.getBoardWidth() / Tile.tileSize][model.getBoardHeight() / Tile.tileSize];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = new Rectangle(SnakeGameModel.getTileSize(), SnakeGameModel.getTileSize());
                grid[x][y].setFill(Color.GREEN);
                grid[x][y].setStroke(Color.BLACK);
                grid[x][y].setTranslateX(x * SnakeGameModel.getTileSize());
                grid[x][y].setTranslateY(y * SnakeGameModel.getTileSize());
                getChildren().add(grid[x][y]);
            }
        }

        scoreText = new Text("Score: 0");
        scoreText.setFont(Font.font(20));
        scoreText.setFill(Color.WHITE);
        scoreText.setTranslateX(10);
        scoreText.setTranslateY(30);
        getChildren().add(scoreText);

        highScoreText = new Text("High Score: " + model.getCurrentHighScore());
        highScoreText.setFont(Font.font(20));
        highScoreText.setFill(Color.WHITE);
        highScoreText.setTranslateX(model.getBoardWidth() - 150);
        highScoreText.setTranslateY(30);
        getChildren().add(highScoreText);
    }

    public void update() {
        for (Rectangle[] row : grid) {
            for (Rectangle cell : row) {
                cell.setFill(Color.GREEN);
            }
        }

        for (Tile segment : model.getSnakeBody()) {
            if (segment.x >= 0 && segment.x < grid.length && segment.y >= 0 && segment.y < grid[0].length) {
                Rectangle segmentRect = grid[segment.x][segment.y];
                segmentRect.setFill(Color.BLACK);
            }
        }

        Tile foodPosition = model.getFood();
        grid[foodPosition.x][foodPosition.y].setFill(Color.ORANGE);

        if (model.isGameOver()) {
            scoreText.setText("Game Over: " + model.getSnakeBody().size());
            scoreText.setFill(Color.RED);
        } else {
            scoreText.setText("Score: " + model.getSnakeBody().size());
            scoreText.setFill(Color.WHITE);
        }
        highScoreText.setText("High Score: " + model.getCurrentHighScore());
    }

}
