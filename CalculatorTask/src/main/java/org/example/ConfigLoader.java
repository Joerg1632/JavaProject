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
            System.out.println("Error loading configuration from file: " + configFile);
        }

        return properties;
    }
}
