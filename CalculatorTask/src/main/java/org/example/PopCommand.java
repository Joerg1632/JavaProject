package org.example;

public class PopCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
        if (!context.stack.isEmpty()) {
            context.stack.pop();
        } else {
            System.out.println("Error: Stack is empty.");
        }
    }
}
