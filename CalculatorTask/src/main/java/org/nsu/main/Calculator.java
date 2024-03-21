package org.nsu.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nsu.commands.Command;
import org.nsu.data.ExecutionContext;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.exceptions.InvalidParameterException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {
    private static final Logger logger = LogManager.getLogger(Calculator.class);
    private final ExecutionContext context;

    public Calculator() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> parameters = new HashMap<>();
        this.context = new ExecutionContext(stack, parameters);
    }

    public void processCommand(Command command) throws CommandExecutionException, InvalidParameterException {
        try {
            command.execute(context);
            logger.debug("Stack: {}", context.stack);
            logger.debug("Parameters: {}", context.parameters);
        } catch (CommandExecutionException | InvalidParameterException e) {
            throw e;
        }
    }
}
