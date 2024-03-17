package org.example;

public class CommandCreationException extends RuntimeException {
    public CommandCreationException(String message) {
        super(message);
    }

    public CommandCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
