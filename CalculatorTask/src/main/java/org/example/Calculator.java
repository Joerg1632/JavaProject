package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class Calculator {
    private static final Logger logger = LogManager.getLogger(Calculator.class);

    public static void processCommands(BufferedReader reader, CommandFactoryClass factory,
                                       Stack<Double> stack, Map<String, Double> parameters) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("Processing command: {}", line);

            try {
                Command command = factory.createCommand(line);
                command.execute(stack, parameters);
                logger.debug("Stack: {}", stack);
                logger.debug("Parameters: {}", parameters);
            } catch (Exception e) {
                logger.error("Error during command execution: {}", e.getMessage());
                throw new RuntimeException("Error during command execution", e);
            }
        }
    }
}
