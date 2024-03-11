package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;

public class Calculator {
    private static final Logger logger = LogManager.getLogger(Calculator.class);

    public static void processCommands(BufferedReader reader, CommandFactoryClass factory,
                                       ExecutionContext context) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("Processing command: {}", line);

            try {
                Command command = factory.createCommand(line);
                command.execute(context);
                logger.debug("Stack: {}", context.stack);
                logger.debug("Parameters: {}", context.parameters);
            } catch (Exception e) {
                logger.error("Error during command execution");
                System.out.println("Error during command execution");
            }
        }
    }
}
