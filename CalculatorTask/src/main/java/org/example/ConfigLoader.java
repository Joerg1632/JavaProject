package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

    public static void loadConfig(String configFile, Map<String, String> commandMap) {
        Properties properties = new Properties();

        try (FileReader reader = new FileReader(configFile)) {
            properties.load(reader);

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                commandMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
