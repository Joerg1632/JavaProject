package org.example;

public class PrintCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        if (!context.stack.isEmpty()) {
            double topElement = context.stack.peek();
            System.out.println(topElement);
        } else {
            throw new CommandExecutionException("Error: Stack is empty.");
        }
    }
}

