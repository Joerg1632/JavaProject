package org.example;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Stack;

public class SqrtCommand implements Command {
    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        try {
            if (!stack.isEmpty()) {
                double topElement = stack.pop();
                double result = Math.sqrt(topElement);
                stack.push(result);
            } else {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Not enough operands on the stack for square root.");
        }
    }
}

