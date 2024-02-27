package org.example;

import java.util.Map;
import java.util.Stack;

public class PrintCommand implements Command {
    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        if (!stack.isEmpty()) {
            double topElement = stack.peek();
            System.out.println("Top element of the stack: " + topElement);
        } else {
            System.out.println("Stack is empty.");
        }
    }
}

