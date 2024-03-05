package org.example;

import java.util.EmptyStackException;

public class SqrtCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        try {
            if (!context.stack.isEmpty()) {
                double topElement = context.stack.pop();
                double result = Math.sqrt(topElement);
                context.stack.push(result);
            } else {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Not enough operands on the stack for square root.");
        }
    }
}

