package org.nsu.commands;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.data.ExecutionContext;

public class DivideCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        if (context.stack.size() < 2) {
            throw new CommandExecutionException("Not enough operands for division.");
        }

        double divisor = context.stack.pop();
        double dividend = context.stack.pop();

        if (divisor != 0) {
            double result = dividend / divisor;
            context.stack.push(result);
        } else {
            context.stack.push(dividend);
            context.stack.push(divisor);
            throw new CommandExecutionException("Division by zero. Result undefined.");
        }
    }
}
