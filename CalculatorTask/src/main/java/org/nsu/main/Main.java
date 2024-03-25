package org.nsu.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nsu.commands.Command;
import org.nsu.exceptions.*;
import org.nsu.util.CommandFactoryClass;
import org.nsu.util.ConfigLoader;
import org.nsu.util.LineParser;
import org.nsu.util.ReaderManager;
import org.nsu.commands.ParameterizedCommand;

import java.io.BufferedReader;
import java.util.Properties;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        CommandFactoryClass factory = new CommandFactoryClass();
        LineParser lineParser = new LineParser();

        try (ReaderManager readerManager = new ReaderManager()) {
            BufferedReader reader = readerManager.createReader(args);
            Properties properties = ConfigLoader.loadConfig("/config.properties");
            factory.setCommandProperties(properties);
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("Processing command: {}", line);
                lineParser.read(line);

                try{
                String commandName = lineParser.getCommandName();
                Command command = factory.createCommand(commandName);
                        if (command instanceof ParameterizedCommand){
                            ((ParameterizedCommand) command).setParameters(lineParser.getTokens());
                        }
                            calculator.processCommand(command);
                } catch (CommandExecutionException e) {
                    logger.error("Error during command execution: {}", e.getMessage());
                } catch (InvalidParameterException e) {
                    logger.error("Invalid parameter: {}", e.getMessage());
                } catch (EmptyLineException e) {
                    logger.error("Empty line encountered: {}", e.getMessage());
                }
            }
        } catch (CommandCreationException e) {
            logger.error("Error creating command: {}", e.getMessage());
        }  catch (ConfigurationLoadException e) {
            logger.error("Error loading configuration: {}", e.getMessage());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}