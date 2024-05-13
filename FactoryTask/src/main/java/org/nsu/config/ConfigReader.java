package org.nsu.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public enum Settings {
        storageBodySize,
        storageEngineSize,
        storageAccessorySize,
        storageAutoSize,
        accessorySuppliers,
        workers,
        dealers,
        logSale
    }

    public ConfigReader() {
        properties = new Properties();
        loadConfig();
    }

    public Object get(Settings name) {
        String value = properties.getProperty(name.name());
        if (value == null) {
            return null;
        }
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }
        return Integer.parseInt(value);
    }

    private void loadConfig() {
        String path = "src/main/resources/config.properties";
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
