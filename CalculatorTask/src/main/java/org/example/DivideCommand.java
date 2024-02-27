package org.example;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Stack;

public class DivideCommand implements Command {
    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        try {
            if (stack.size() >= 2) {
                double divisor = stack.pop();
                double dividend = stack.pop();

                if (divisor != 0) {
                    double result = dividend / divisor;
                    stack.push(result);
                } else {
                    throw new ArithmeticException("Division by zero.");
                }
            } else {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException | ArithmeticException e) {
            throw new IllegalArgumentException("Error during division: " + e.getMessage());
        }
    }
}

