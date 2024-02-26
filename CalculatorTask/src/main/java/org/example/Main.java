package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Double> stack = new Stack<>();
        CommandFactoryClass factory = new CommandFactoryClass();

        try {
            BufferedReader reader;
            if (args.length > 0) {
                reader = new BufferedReader(new FileReader(args[0]));
            } else {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                Command command = factory.createCommand(line);
                command.execute(stack);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
