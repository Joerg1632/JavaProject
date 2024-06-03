package org.nsu.communication.message;

public class ServerToClientMessage extends Message {
    public String eventName;
    public String message;
    public String name;

    public ServerToClientMessage(String commandName, String session, String eventName, String message, String name) {
        super(commandName, session);
        this.eventName = eventName;
        this.message = message;
        this.name = name;
    }
}
