package org.nsu.util;

public class LineParser {
    private String line;

    public void read(String line) {
        this.line = line;
    }

    public String getCommandName() {
        String[] tokens = line.split("\\s+");
        return tokens[0];
    }

    public String[] getTokens() {
        return line.split("\\s+");
    }
}
