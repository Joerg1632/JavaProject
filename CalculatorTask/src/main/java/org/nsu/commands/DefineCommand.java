package org.nsu.commands;

import org.nsu.data.ExecutionContext;
import org.nsu.exceptions.CommandExecutionException;
import org.nsu.exceptions.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefineCommand implements ParameterizedCommand {
    private String paramName;
    private double paramValue;

    @Override
    public void setParameters(String[] tokens) throws InvalidParameterException, CommandExecutionException {
        if (tokens.length == 3) {
            boolean validVariable = isValidVariable(tokens[1]);
            boolean validNumber = isValidNumber(tokens[2]);

            if (validVariable && validNumber) {
                this.paramName = tokens[1];
                this.paramValue = Double.parseDouble(tokens[2]);
            } else if (!validVariable && !validNumber) {
                throw new InvalidParameterException("Ignoring invalid DEFINE command: BOTH PARAMETERS ARE INVALID.");
            } else if (!validVariable) {
                throw new InvalidParameterException("Ignoring invalid DEFINE command: Parameter '" + tokens[1] + "' is not a valid variable. FIRST PARAMETER INVALID");
            } else {
                throw new InvalidParameterException("Ignoring invalid DEFINE command: Cannot parse '" + tokens[2] + "' as a valid number. SECOND PARAMETER INVALID");
            }
        } else if(tokens.length > 3){
            throw new CommandExecutionException("Ignoring invalid DEFINE command: " + String.join(" ", tokens) + " too much parameters");
        }
        else{
            throw new CommandExecutionException("Ignoring invalid DEFINE command: " + String.join(" ", tokens) + " not enough parameters");
        }
    }

    @Override
    public void execute(ExecutionContext context) {
        context.parameters.put(paramName, paramValue);
    }

    private boolean isValidVariable(String variable) {
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(variable);
        return matcher.matches();
    }

    private boolean isValidNumber(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
