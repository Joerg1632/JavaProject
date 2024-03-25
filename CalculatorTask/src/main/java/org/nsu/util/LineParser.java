package org.nsu.util;

import org.nsu.exceptions.EmptyLineException;

public class LineParser {
    private String line;

    public void read(String line) {
        this.line = line;
    }

    public String getCommandName() throws EmptyLineException {
        if (line == null || line.trim().isEmpty()) {
            throw new EmptyLineException("The line is null or empty.");
        }
        String[] tokens = line.split("\\s+");
        return tokens[0];
    }

    public String[] getTokens() {
        return line.split("\\s+");
    }
}
