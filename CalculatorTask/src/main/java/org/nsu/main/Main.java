package org.nsu.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nsu.commands.Command;
import org.nsu.util.CommandFactoryClass;
import org.nsu.util.ReaderManager;
import org.nsu.commands.ParameterizedCommand;
import java.io.BufferedReader;
import org.nsu.exceptions.CommandCreationException;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.exceptions.InvalidParameterException;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        CommandFactoryClass factory = new CommandFactoryClass("src/main/java/org/nsu/main/config.properties");

        try (ReaderManager readerManager = new ReaderManager()) {
            BufferedReader reader = readerManager.createReader(args);
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("Processing command: {}", line);
                try {
                    String[] tokens = line.split("\\s+");
                    String commandName = tokens[0];
                    Command command = factory.createCommand(commandName);
                    if (command instanceof ParameterizedCommand){
                        ((ParameterizedCommand) command).setParameters(tokens);
                    }
                    calculator.processCommand(command);
                } catch (CommandCreationException e) {
                    logger.error("Error creating command: {}", e.getMessage());
                    System.out.println("Error creating command: " + e.getMessage());
                } catch (CommandExecutionException e) {
                    logger.error("Error during command execution: {}", e.getMessage());
                    System.out.println("Error during command execution: " + e.getMessage());
                } catch (InvalidParameterException e) {
                    logger.error("Invalid parameter: {}", e.getMessage());
                    System.out.println("Invalid parameter: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error during command processing");
        }
    }
}