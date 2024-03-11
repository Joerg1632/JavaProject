package org.example;

import java.util.EmptyStackException;

public class SqrtCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        if (!context.stack.isEmpty()) {
            double topElement = context.stack.pop();
            double result = Math.sqrt(topElement);
            context.stack.push(result);
        } else {
            System.err.println("Error: Not enough operands on the stack for square root.");
        }
    }
}

