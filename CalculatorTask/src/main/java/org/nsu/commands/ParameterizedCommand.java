package org.nsu.commands;

import org.nsu.exceptions.CommandExecutionException;
import org.nsu.exceptions.InvalidParameterException;

public interface ParameterizedCommand extends Command {
    void setParameters(String[] parameters) throws InvalidParameterException, CommandExecutionException;
}
