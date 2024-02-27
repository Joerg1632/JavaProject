package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> parameters = new HashMap<>();
        CommandFactoryClass factory = new CommandFactoryClass("src/main/java/org/example/config.txt");

        try {
            ReaderManager readerManager = new ReaderManager();
            BufferedReader reader = readerManager.createReader(args);
            Calculator.processCommands(reader, factory, stack, parameters);
            readerManager.closeReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
