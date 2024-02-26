package org.example;
import java.util.HashMap;
import java.util.Map;

public class CommandFactoryClass {
    private Map<String, String> commandMap;

    public CommandFactoryClass() {
        commandMap = new HashMap<>();
    }

    public Command createCommand(String line) {
        String[] tokens = line.split("\\s+");
        String commandName = tokens[0];

        try {
            String className = commandMap.get(commandName);
            Class<?> commandClass = Class.forName(className);
            return (Command) commandClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}