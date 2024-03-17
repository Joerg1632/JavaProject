package org.example;

public class AddCommand implements Command {
    @Override
    public void execute(ExecutionContext context) throws CommandExecutionException {
        if (context.stack.size() < 2) {
            throw new CommandExecutionException("Not enough operands for addition.");
        }

        double operand2 = context.stack.pop();
        double operand1 = context.stack.pop();
        double result = operand1 + operand2;
        context.stack.push(result);
    }
}
