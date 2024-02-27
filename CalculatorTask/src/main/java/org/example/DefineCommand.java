package org.example;

import java.util.Map;
import java.util.Stack;

public class DefineCommand implements ParameterizedCommand {
    private String paramName;
    private double paramValue;

    @Override
    public void setParameters(String[] tokens) {
        if (tokens.length == 3) {
            this.paramName = tokens[1].toUpperCase();
            this.paramValue = Double.parseDouble(tokens[2]);
        } else {
            System.out.println("Ignoring invalid DEFINE command: " + String.join(" ", tokens));
        }
    }

    @Override
    public void execute(Stack<Double> stack, Map<String, Double> parameters) {
        parameters.put(paramName, paramValue);
    }
}
