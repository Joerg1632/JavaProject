package org.example;

public class DefineCommand implements ParameterizedCommand {
    private String paramName;
    private double paramValue;

    @Override
    public void setParameters(String[] tokens) {
        if (tokens.length == 3) {
            this.paramName = tokens[1].toUpperCase();
            try {
                this.paramValue = Double.parseDouble(tokens[2]);
            } catch (NumberFormatException e) {
                System.out.println("Ignoring invalid DEFINE command. Cannot parse '" + tokens[2] + "' as a valid number.");
            }
        } else {
            System.out.println("Ignoring invalid DEFINE command: " + String.join(" ", tokens) + "not enough parameters");
        }
    }

    @Override
    public void execute(ExecutionContext context) {
        context.parameters.put(paramName, paramValue);
    }
}
