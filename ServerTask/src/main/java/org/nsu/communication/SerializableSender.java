package org.nsu.communication;

import org.nsu.communication.message.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SerializableSender implements Sender {

    public String tryToConnect(ObjectOutputStream outputStream, ObjectInputStream inputStream, String username) throws IOException, ClassNotFoundException {
        sendLoginMessage(outputStream, username);
        return receiveSessionID(inputStream);
    }

    private void sendLoginMessage(ObjectOutputStream outputStream, String username) throws IOException {
        LoginMessage loginMessage = new LoginMessage("login", null, username);
        outputStream.writeObject(loginMessage);
        outputStream.flush();
    }

    private String receiveSessionID(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Message sessionMessage = (Message) inputStream.readObject();
        return sessionMessage.session;
    }

    public String getServerMessage(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ServerToClientMessage message = (ServerToClientMessage) in.readObject();
        return formatServerMessage(message);
    }

    private String formatServerMessage(ServerToClientMessage message) {
        return switch (message.commandName) {
            case "message" -> message.message + "\n";
            case "userlogin" -> message.name + " connected\n";
            case "userlogout" -> message.name + " disconnected\n";
            case "history" -> formatChatHistory(message.message);
            case "list" -> message.message;
            default -> null;
        };
    }

    private String formatChatHistory(String history) {
        return history.replaceAll(", ", "\n").replaceAll("[\\[\\]]", "") + "\n";
    }

    public void sendLogoutCommand(ObjectOutputStream out, String session) throws IOException {
        sendClientMessage(out, new LogoutMessage("logout", session));
    }

    public void requestUserList(ObjectOutputStream out, String session) throws IOException {
        sendClientMessage(out, new ClientToServerMessage("list", session, "list"));
    }

    public void sendMessageToServer(ObjectOutputStream out, String messageContent, String session) throws IOException {
        sendClientMessage(out, new ClientToServerMessage("message", session, messageContent));
    }

    private void sendClientMessage(ObjectOutputStream out, Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public Message getClientMessage(ObjectInputStream in) throws IOException, ClassNotFoundException {
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

    public void sendUserListResponse(ObjectOutputStream out, Map<String, String> sessionMap, String sessionID) throws IOException {
        List<String> users = extractUserList(sessionMap);
        ServerToClientMessage listUsersMessage = new ServerToClientMessage("list", sessionID, "list", formatUserList(users), "Server");
        sendMessage(out, listUsersMessage);
    }

    private List<String> extractUserList(Map<String, String> sessionMap) {
        return new ArrayList<>(sessionMap.values());
    }

    private String formatUserList(List<String> users) {
        return "users\n" + users.toString().replaceAll(", ", "\n").replaceAll("[\\[\\]]", "") + "\n";
    }
}
