package org.nsu.commands;

import org.nsu.data.ExecutionContext;

public interface Command {
    void execute(ExecutionContext context);
}

