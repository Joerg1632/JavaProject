package org.nsu.commands;

import org.nsu.exceptions.CommandCreationException;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.data.ExecutionContext;
import org.nsu.exceptions.InvalidParameterException;

public class PushCommand implements ParameterizedCommand {
    private String paramValue;

    @Override
    public void setParameters(String[] tokens) throws CommandCreationException {
        if (tokens.length == 2) {
            this.paramValue = tokens[1];
        } else {
            throw new CommandCreationException("Invalid PUSH command: " + String.join(" ", tokens));
        }
    }

    @Override
    public void execute(ExecutionContext context) throws CommandExecutionException, InvalidParameterException {
        try {
            if (paramValue.matches("-?\\d+(\\.\\d+)?")) {  // if number
                context.stack.push(Double.parseDouble(paramValue));
            } else if (context.parameters.containsKey(paramValue)) {
                double variableValue = context.parameters.get(paramValue);
                context.stack.push(variableValue);
            } else {
                throw new InvalidParameterException("Undefined parameter " + paramValue);
            }
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid parameter value: " + paramValue, e);
        }
    }
}
