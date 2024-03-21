package org.nsu.commands;

public interface ParameterizedCommand extends Command {
    void setParameters(String[] parameters);
}
