package org.example;

import java.util.EmptyStackException;

public class AddCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        try {
            if (context.stack.size() >= 2) {
                double operand2 = context.stack.pop();
                double operand1 = context.stack.pop();
                double result = operand1 + operand2;
                context.stack.push(result);
            } else {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Not enough operands on the stack for addition.");
        }
    }
}

