package org.nsu.commands;

import org.nsu.exceptions.CommandExecutionException;
import org.nsu.data.ExecutionContext;

public class SubtractCommand implements Command {
    @Override
    public void execute(ExecutionContext context) throws CommandExecutionException {
        if (context.stack.size() >= 2) {
            double operand2 = context.stack.pop();
            double operand1 = context.stack.pop();
            double result = operand1 - operand2;
            context.stack.push(result);
        } else {
            throw new CommandExecutionException("Not enough operands on the stack for subtraction.");
        }
    }
}
