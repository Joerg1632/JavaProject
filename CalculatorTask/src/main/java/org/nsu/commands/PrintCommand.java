package org.nsu.commands;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.data.ExecutionContext;

public class PrintCommand implements Command {
    @Override
    public void execute(ExecutionContext context) throws CommandExecutionException {
        if (!context.stack.isEmpty()) {
            double topElement = context.stack.peek();
            System.out.println(topElement);
        } else {
            throw new CommandExecutionException("Error: Stack is empty.");
        }
    }
}

