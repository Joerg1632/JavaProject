package org.example;

public class MultiplyCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        if (context.stack.size() >= 2) {
            double operand2 = context.stack.pop();
            double operand1 = context.stack.pop();
            double result = operand1 * operand2;
            context.stack.push(result);
        } else {
            System.err.println("Error: Not enough operands on the stack for multiplication.");
        }
    }
}
