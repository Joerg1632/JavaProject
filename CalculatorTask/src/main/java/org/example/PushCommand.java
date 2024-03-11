package org.example;

public class PushCommand implements ParameterizedCommand {
    private String paramValue;

    @Override
    public void setParameters(String[] tokens) {
        if (tokens.length == 2) {
            this.paramValue = tokens[1].toUpperCase();
        } else {
            System.out.println("Ignoring invalid PUSH command: " + tokens);
        }
    }

    @Override
    public void execute(ExecutionContext context) {
        if (paramValue.matches("-?\\d+(\\.\\d+)?")) {  //if number
            context.stack.push(Double.parseDouble(paramValue));
        } else if (context.parameters.containsKey(paramValue)) {
            double variableValue = context.parameters.get(paramValue);
            context.stack.push(variableValue);
        } else {
            System.out.println("Undefined parameter " + paramValue);
        }
    }
}

