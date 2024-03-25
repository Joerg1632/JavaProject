package org.nsu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.nsu.exceptions.ConfigurationLoadException;

public class ConfigLoader {

    public static Properties loadConfig(String configFile) {
        Properties properties = new Properties();

        try (InputStream inputStream = ConfigLoader.class.getResourceAsStream(configFile)) {
            if (inputStream == null) {
                throw new ConfigurationLoadException("Configuration file not found: " + configFile);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ConfigurationLoadException("Error loading configuration from file: " + configFile, e);
        }

        return properties;
    }
}
