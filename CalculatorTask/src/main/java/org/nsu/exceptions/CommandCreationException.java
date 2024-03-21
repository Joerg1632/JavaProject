package org.nsu.exceptions;

public class CommandCreationException extends RuntimeException {
    public CommandCreationException(String message) {
        super(message);
    }

    public CommandCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
