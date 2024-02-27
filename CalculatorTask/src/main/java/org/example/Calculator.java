package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class Calculator {
    public static void processCommands(BufferedReader reader, CommandFactoryClass factory,
                                       Stack<Double> stack, Map<String, Double> parameters) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            Command command = factory.createCommand(line);
            command.execute(stack, parameters);
        }
    }
}
