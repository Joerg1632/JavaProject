package org.example;

import java.util.Map;
import java.util.Stack;

public class ExecutionContext {
    public Stack<Double> stack;
    public Map<String, Double> parameters;

    public ExecutionContext(Stack<Double> stack, Map<String, Double> parameters) {
        this.stack = stack;
        this.parameters = parameters;
    }
}
