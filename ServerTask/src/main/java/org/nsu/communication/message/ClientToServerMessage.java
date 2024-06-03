package org.nsu.communication.message;

public class ClientToServerMessage extends Message {
    public String message;

    public ClientToServerMessage(String commandName, String session, String message) {
        super(commandName, session);
        this.message = message;
    }
}
