package org.nsu.communication;

import org.nsu.communication.message.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SerializableSender implements Sender {

    public String tryToConnect(ObjectOutputStream outputStream, ObjectInputStream inputStream, String username) throws IOException, ClassNotFoundException {

        LoginMessage loginMessage = new LoginMessage("login", null, username);
        outputStream.writeObject(loginMessage);
        outputStream.flush();

        Message sessionMessage = (Message) inputStream.readObject();
        return sessionMessage.session;
    }

    public String getServerMessage(ObjectInputStream in) throws IOException, ClassNotFoundException {

        ServerToClientMessage message = (ServerToClientMessage) in.readObject();
        return switch (message.commandName) {
            case "message" -> (message.message + "\n");
            case "userlogin" -> message.name + "connected\n";
            case "userlogout" -> message.name + "disconnected\n";
            case "history" -> message.message.replaceAll(", ", "\n").replaceAll("[\\[\\]]", "") + "\n";
            case "list" -> message.message;
            default -> null;
        };
    }

    public void sendLogoutCommand(ObjectOutputStream out, String session) throws IOException {

        LogoutMessage logoutMessage = new LogoutMessage("logout", session);
        out.writeObject(logoutMessage);
        out.flush();
    }

    public void requestUserList(ObjectOutputStream out, String session) throws IOException {

        ClientToServerMessage userListMessage = new ClientToServerMessage("list", session, "list");
        out.writeObject(userListMessage);
        out.flush();
    }

    public void sendMessageToServer(ObjectOutputStream out, String messageContent, String session) throws IOException {

        ClientToServerMessage message = new ClientToServerMessage("message", session, messageContent);
        out.writeObject(message);
        out.flush();
    }

    public Message getClientMessage(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {

        return (Message) in.readObject();
    }

    public void sendMessage(ObjectOutputStream out, ServerToClientMessage message) throws IOException {

        out.writeObject(message);
        out.flush();
    }

    public void sendChatHistory(ObjectOutputStream out, String sessionID, LinkedList<String> chatHistory) throws IOException {

        ServerToClientMessage chatHistoryResponse = new ServerToClientMessage("history", sessionID, "history", chatHistory.toString(), "Server");
        sendMessage(out, chatHistoryResponse);
    }

    public void sendUserListResponse(ObjectOutputStream out, Map<String, String> sessionMap, String sessionID)throws IOException {

        List<String> users = new ArrayList<>();
        for (Map.Entry<String, String> entry : sessionMap.entrySet()) {
            users.add(entry.getValue());
        }

        ServerToClientMessage listUsersMessage = new ServerToClientMessage("list", sessionID, "list", "users\n" + users.toString().replaceAll(", ", "\n").replaceAll("[\\[\\]]", "") + "\n", "Server");
        out.writeObject(listUsersMessage);
        out.flush();
    }
}