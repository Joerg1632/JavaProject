package org.example;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CommandLineTest {

    @Test
    public void testCommandExecution() {
        String input = "PUSH 5\nPUSH 3\n+\nPRINT";

        BufferedReader reader = new BufferedReader(new StringReader(input));

        CommandFactoryClass factory = new CommandFactoryClass("src/main/java/org/example/config.txt");

        Stack<Double> stack = new Stack<>();
        Map<String, Double> parameters = Map.of();

        PrintStream originalOut = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Calculator.processCommands(reader, factory, stack, parameters);

            if (!stack.isEmpty()) {
                assertEquals(Optional.of(8.0), Optional.of(stack.pop()));
            } else {
                fail("Stack is empty");
            }

            assertEquals("8.0", outContent.toString().trim());


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(originalOut);
        }
    }
}
