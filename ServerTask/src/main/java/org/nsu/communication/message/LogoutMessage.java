package org.nsu.communication.message;

public class LogoutMessage extends Message {

    public LogoutMessage(String commandName, String session) {
        super(commandName, session);
    }
}
