package org.nsu.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.nsu.exceptions.ConfigurationLoadException;

public class ConfigLoader {

    public static Properties loadConfig(String configFile) {
        Properties properties = new Properties();

        try (FileReader reader = new FileReader(configFile)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new ConfigurationLoadException("Error loading configuration from file: " + configFile, e);
        }

        return properties;
    }
}
