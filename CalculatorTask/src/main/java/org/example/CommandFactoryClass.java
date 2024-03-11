package org.example;

import java.util.Properties;

public class CommandFactoryClass {
    private final Properties commandProperties;

    public CommandFactoryClass(String configFile) {
        this.commandProperties = ConfigLoader.loadConfig(configFile); // Load classes from file
    }

    public Command createCommand(String line) {
        String[] tokens = line.split("\\s+");
        String commandName = tokens[0];
        try {
            String className = commandProperties.getProperty(commandName);
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
                System.out.println(Info.getAvailableCommandsInfo(commandProperties));
                return new NullCommand();
            }
        } catch (Exception e) {
            System.out.println("Error creating command");
            return new NullCommand();
        }
    }
}