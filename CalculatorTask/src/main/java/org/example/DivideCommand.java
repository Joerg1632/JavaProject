package org.example;

import java.util.EmptyStackException;

public class DivideCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {

            if (context.stack.size() < 2) {
                System.out.println("Error: Not enough operands for division.");
            } else {
                double divisor = context.stack.pop();
                double dividend = context.stack.pop();

                if (divisor != 0) {
                    double result = dividend / divisor;
                    context.stack.push(result);
                } else {
                    context.stack.push(dividend);
                    context.stack.push(divisor);
                    System.out.println("Error: Division by zero. Result undefined.");
                }
            }
        }
    }

