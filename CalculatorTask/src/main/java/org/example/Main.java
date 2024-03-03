package org.example;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> parameters = new HashMap<>();
        CommandFactoryClass factory = new CommandFactoryClass("src/main/java/org/example/config.properties");

        try(ReaderManager readerManager = new ReaderManager()) {
            BufferedReader reader = readerManager.createReader(args);
            Calculator.processCommands(reader, factory, stack, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
