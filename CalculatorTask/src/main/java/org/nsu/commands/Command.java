package org.nsu.commands;

import org.nsu.data.ExecutionContext;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.exceptions.InvalidParameterException;

public interface Command {
    void execute(ExecutionContext context) throws CommandExecutionException, InvalidParameterException;
}

