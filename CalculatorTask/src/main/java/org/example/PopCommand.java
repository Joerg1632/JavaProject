package org.example;

public class PopCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        if (!context.stack.isEmpty()) {
            context.stack.pop();
        } else {
            throw new CommandExecutionException("Error: Stack is empty.");
        }
    }
}
