package org.example;

import java.util.EmptyStackException;

public class DivideCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        try {
            if (context.stack.size() >= 2) {
                double divisor = context.stack.pop();
                double dividend = context.stack.pop();

                if (divisor != 0) {
                    double result = dividend / divisor;
                    context.stack.push(result);
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

