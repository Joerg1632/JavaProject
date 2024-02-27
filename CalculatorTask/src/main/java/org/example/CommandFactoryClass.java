package org.example;
import java.util.HashMap;
import java.util.Map;

public class CommandFactoryClass {
    private final Map<String, String> commandMap;

    public CommandFactoryClass(String configFile) {
        commandMap = new HashMap<>();
        ConfigLoader.loadConfig(configFile, commandMap); // Load classes
    }

    public Command createCommand(String line) {
        String[] tokens = line.split("\\s+");
        String commandName = tokens[0];
        try {
            String className = commandMap.get(commandName);
            if (className != null){
                ClassLoader classLoader = getClass().getClassLoader();
                Class<?> commandClass = classLoader.loadClass(className);

                if (ParameterizedCommand.class.isAssignableFrom(commandClass)) {
                    ParameterizedCommand parameterizedCommand = (ParameterizedCommand) commandClass.getDeclaredConstructor().newInstance();
                    parameterizedCommand.setParameters(tokens);
                    return parameterizedCommand;
                } else {
                    return (Command) commandClass.getDeclaredConstructor().newInstance();
                }
            }
            else{
                System.out.println("Error: The command was entered incorrectly");
                CommandInformation Info = new CommandInformation();
                System.out.println(Info.getAvailableCommandsInfo(commandMap));
                return new NullCommand();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}