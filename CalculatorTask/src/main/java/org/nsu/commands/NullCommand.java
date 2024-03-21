package org.nsu.commands;
import org.nsu.data.ExecutionContext;

public class NullCommand implements Command {
    @Override
    public void execute(ExecutionContext context) {
    }
}
