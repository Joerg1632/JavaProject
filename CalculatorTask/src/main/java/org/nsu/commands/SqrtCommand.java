package org.nsu.commands;


import org.nsu.exceptions.CommandExecutionException;
import org.nsu.data.ExecutionContext;

public class SqrtCommand implements Command {
    @Override
    public void execute(ExecutionContext context) throws CommandExecutionException {
        if (!context.stack.isEmpty()) {
            double topElement = context.stack.pop();
            double result = Math.sqrt(topElement);
            context.stack.push(result);
        } else {
            throw new CommandExecutionException("Error: Not enough operands on the stack for square root.");
        }
    }
}

