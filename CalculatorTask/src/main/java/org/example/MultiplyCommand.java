package org.example;

import java.util.Map;
import java.util.Stack;

public class MultiplyCommand implements Command {
    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        if (stack.size() >= 2) {
            double operand2 = stack.pop();
            double operand1 = stack.pop();
            double result = operand1 * operand2;
            stack.push(result);
        } else {
            throw new IllegalArgumentException("Not enough operands on the stack for multiplication.");
        }
    }
}

