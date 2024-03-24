import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.nsu.commands.Command;
import org.nsu.commands.ParameterizedCommand;
import org.nsu.data.ExecutionContext;
import org.nsu.main.Calculator;
import org.nsu.util.CommandFactoryClass;
import org.nsu.util.LineParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Nested
class CalculatorTest {

    private static final double DELTA = 1e-10;

    @Test
    public void testArithmeticOperations() {
        String input = "PUSH 5\nPUSH 3\n+";
        assertCalculatorResult(input, 8.0);
    }

    @Test
    public void testArithmeticOperationsWithVariables() {
        String input = "DEFINE x 5\nPUSH x\nPUSH 3\n+";
        assertCalculatorResult(input, 8.0);
    }

    @Test
    public void testStackManipulation() {
        String input = "PUSH 5\nPUSH 3\n-";
        assertCalculatorResult(input, 2.0);
    }

    @Test
    public void testVariableAndArithmeticOperations() {
        String input = "DEFINE x 5\nPUSH x\nPUSH 3\n*";
        assertCalculatorResult(input, 15.0);
    }

    @Test
    public void testSQRTAndPrintOperations() {
        String input = "PUSH 25\nSQRT\nPRINT";
        assertCalculatorOutput(input, "5.0");
    }

    @Test
    public void testCombinationOfOperations() {
        String input = "PUSH 9\nDEFINE x 5\nDEFINE y 4\nPUSH x\nPUSH y\n+\n*\nSQRT";
        assertCalculatorResult(input, 9.0);
    }

    private void assertCalculatorResult(String input, double expectedResult) {
        Calculator calculator = new Calculator();
        CommandFactoryClass factory = new CommandFactoryClass();
        Properties properties = new Properties();
        properties.setProperty("PUSH", "org.nsu.commands.PushCommand");
        factory.setCommandProperties(properties);

        try (InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            LineParser lineParser = new LineParser();
            String line;
            while ((line = reader.readLine()) != null) {
                lineParser.read(line);
                String commandName = lineParser.getCommandName();
                Command command = factory.createCommand(commandName);
                if (command != null && command instanceof ParameterizedCommand) {
                    ((ParameterizedCommand) command).setParameters(lineParser.getTokens());
                }
                calculator.processCommand(command);
            }

            assertEquals(expectedResult, calculator.getContext().getStack().pop(), DELTA);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

    private void assertCalculatorOutput(String input, String expectedOutput) {
        try (InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {

            System.setOut(new PrintStream(outContent));
            Stack<Double> stack = new Stack<>();
            Map<String, Double> parameters = new HashMap<>();
            ExecutionContext context = new ExecutionContext(stack, parameters);
            Calculator.processCommands(new BufferedReader(new InputStreamReader(inputStream)),
                    new CommandFactoryClass("src/main/java/org/example/config.properties"), context);

            assertEquals(expectedOutput, outContent.toString().trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

