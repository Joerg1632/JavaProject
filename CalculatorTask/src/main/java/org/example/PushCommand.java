package org.example;

public class PushCommand implements ParameterizedCommand {
    private String paramValue;

    @Override
    public void setParameters(String[] tokens) throws CommandCreationException {
        if (tokens.length == 2) {
            this.paramValue = tokens[1].toUpperCase();
        } else {
            throw new CommandCreationException("Invalid PUSH command: " + String.join(" ", tokens));
        }
    }

    @Override
    public void execute(ExecutionContext context) throws CommandExecutionException {
        try {
            if (paramValue.matches("-?\\d+(\\.\\d+)?")) {  // if number
                context.stack.push(Double.parseDouble(paramValue));
            } else if (context.parameters.containsKey(paramValue)) {
                double variableValue = context.parameters.get(paramValue);
                context.stack.push(variableValue);
            } else {
                throw new CommandExecutionException("Undefined parameter " + paramValue);
            }
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("Invalid parameter value: " + paramValue, e);
        }
    }
}
