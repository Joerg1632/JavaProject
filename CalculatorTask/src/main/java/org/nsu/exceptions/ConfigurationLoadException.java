package org.nsu.exceptions;

public class ConfigurationLoadException extends RuntimeException {

    public ConfigurationLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationLoadException(String message) {
        super(message);
    }
}
