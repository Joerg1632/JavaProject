package org.nsu.controller;

import org.nsu.model.SnakeGameModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class PropertyManager {
    private final Properties properties;
    private final GameController controller;
    private final SnakeGameModel model;

    public PropertyManager(SnakeGameModel model, GameController controller) {
        this.controller = controller;
        this.model = model;
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            int highScore = Integer.parseInt(properties.getProperty("highScore", "0"));
            controller.setCurrentHighScore(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveProperties() {
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            properties.setProperty("highScore", String.valueOf(model.getCurrentHighScore()));
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
