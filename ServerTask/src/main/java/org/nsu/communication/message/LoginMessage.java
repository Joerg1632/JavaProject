package org.nsu.communication.message;

public class LoginMessage extends Message {
    public String userName;

    public LoginMessage(String commandName, String session, String userName) {
        super(commandName, session);
        this.userName = userName;
    }
}