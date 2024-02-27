package org.example;

import java.util.Map;
import java.util.Stack;

public class PopCommand implements Command {
    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        if (!stack.isEmpty()) {
            stack.pop();
        } else {
            throw new IllegalArgumentException("Error: Stack is empty.");
        }
    }
}
