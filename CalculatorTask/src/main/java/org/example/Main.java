package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> parameters = new HashMap<>();
        ExecutionContext context = new ExecutionContext(stack, parameters);
        CommandFactoryClass factory = new CommandFactoryClass("src/main/java/org/example/config.properties");

        try (ReaderManager readerManager = new ReaderManager()) {
            Calculator.processCommands(readerManager.createReader(args), factory, context);
        } catch (Exception e) {
            throw new RuntimeException("Error during command processing", e);
        }
    }
}
