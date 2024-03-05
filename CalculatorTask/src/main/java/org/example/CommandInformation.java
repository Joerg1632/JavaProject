package org.example;

import java.util.Properties;

public class CommandInformation {
    public String getAvailableCommandsInfo(Properties commandProperties) {
        StringBuilder info = new StringBuilder("Available commands:\n");

        for (String commandName : commandProperties.stringPropertyNames()) {
            String className = commandProperties.getProperty(commandName);
            info.append(commandName).append(": ").append(className).append("\n");
        }

        return info.toString();
    }
}
