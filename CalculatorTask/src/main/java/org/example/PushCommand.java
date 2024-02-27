package org.example;

import java.util.Map;
import java.util.Stack;

public class PushCommand implements ParameterizedCommand {
    private String paramName;

    @Override
    public void setParameters(String[] tokens) {
        if (tokens.length == 2) {
            this.paramName = tokens[1].toUpperCase();
        } else {
            System.out.println("Ignoring invalid PUSH command: " + tokens);
        }
    }

    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        if (parameters.containsKey(paramName)) {
            double paramValue = parameters.get(paramName);
            stack.push(paramValue);
        } else {
            throw new IllegalArgumentException("Undefined parameter: " + paramName);
        }
    }
}

