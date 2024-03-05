package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    public static Properties loadConfig(String configFile) {
        Properties properties = new Properties();

        try (FileReader reader = new FileReader(configFile)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration from file: " + configFile, e);
        }

        return properties;
    }
}
