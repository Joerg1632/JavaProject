package org.nsu.controller;

import org.nsu.view.SnakeGameView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class PropertyManager {
    private final Properties properties;
    private final SnakeGameView view;

    public PropertyManager(SnakeGameView view) {
        this.view = view;
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            int highScore = Integer.parseInt(properties.getProperty("highScore", "0"));
            view.setCurrentHighScore(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveProperties() {
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            properties.setProperty("highScore", String.valueOf(view.getCurrentHighScore()));
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
