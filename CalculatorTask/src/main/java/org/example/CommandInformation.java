package org.example;

import java.util.Properties;

public class CommandInformation {
    public String getAvailableCommandsInfo(Properties commandProperties) {
        StringBuilder info = new StringBuilder("Available commands:\n");

        for (String commandName : commandProperties.stringPropertyNames()) {
            String className = commandProperties.getProperty(commandName);
            info.append(formatCommandInfo(commandName)).append("\n");
        }

        return info.toString();
    }

    private String formatCommandInfo(String commandName) {
        switch (commandName) {
            case "PUSH":
                return "Command " + commandName + " {num or var}: Takes a number or variable and pushes it onto the stack";
            case "DEFINE":
                return "Command " + commandName + " {var} {num}: Accepts a number and a variable, defining the variable to be the specified number";
            default:
                return "Command " + commandName + " {}: empty input(Work with stack)";
        }
    }
}
