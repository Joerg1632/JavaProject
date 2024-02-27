package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ConfigLoader {

    public static void loadConfig(String configFile, Map<String, String> commandMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("=");
                if (tokens.length == 2) {
                    commandMap.put(tokens[0], tokens[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
