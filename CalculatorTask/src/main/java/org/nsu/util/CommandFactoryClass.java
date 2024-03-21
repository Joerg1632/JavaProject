package org.nsu.util;


import org.nsu.commands.Command;
import org.nsu.commands.NullCommand;
import org.nsu.commands.ParameterizedCommand;
import org.nsu.exceptions.CommandCreationException;
import org.nsu.exceptions.InvalidParameterException;

import java.util.Properties;

public class CommandFactoryClass {
    private final Properties commandProperties;

    public CommandFactoryClass(String configFile) {
        this.commandProperties = ConfigLoader.loadConfig(configFile);
    }

    public Command createCommand(String commandName) throws InvalidParameterException {
        try {
            String className = commandProperties.getProperty(commandName);
            if (className != null){
                ClassLoader classLoader = getClass().getClassLoader();
                Class<?> commandClass = classLoader.loadClass(className);
                if (ParameterizedCommand.class.isAssignableFrom(commandClass)) {
                    return (ParameterizedCommand) commandClass.getDeclaredConstructor().newInstance();
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
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw new CommandCreationException("Error creating command", e);
        }
    }
}
