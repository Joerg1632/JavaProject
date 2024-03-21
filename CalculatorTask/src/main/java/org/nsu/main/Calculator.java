package org.nsu.main;

import org.nsu.commands.Command;
import org.nsu.data.ExecutionContext;
import org.nsu.exceptions.CommandExecutionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {
    private final ExecutionContext context;

    public Calculator() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> parameters = new HashMap<>();
        this.context = new ExecutionContext(stack, parameters);
    }

    public void processCommand(Command command) {
        try {
            command.execute(context);
        } catch (CommandExecutionException e) {
            System.err.println("Error during command execution: " + e.getMessage());
        }
    }
}
