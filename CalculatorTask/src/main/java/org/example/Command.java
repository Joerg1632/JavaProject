package org.example;
import java.util.Map;
import java.util.Stack;

public interface Command {
    void execute(Stack<Double> stack, Map<String, Double> parameters);
}

