package org.example;

import java.util.Map;

public class CommandInformation{
    public String getAvailableCommandsInfo(Map<String, String> commandMap) {
        StringBuilder info = new StringBuilder("Available commands:\n");

        for (Map.Entry<String, String> entry : commandMap.entrySet()) {
            info.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return info.toString();
    }
}

