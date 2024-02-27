package org.example;
import java.util.Map;
import java.util.Stack;

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
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        if (paramValue.matches("-?\\d+(\\.\\d+)?")) {
            stack.push(Double.parseDouble(paramValue));
        } else if (parameters.containsKey(paramValue)) {
            double variableValue = parameters.get(paramValue);
            stack.push(variableValue);
        } else {
            throw new IllegalArgumentException("Undefined parameter: " + paramValue);
        }
    }
}

