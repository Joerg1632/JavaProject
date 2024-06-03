package org.nsu.server;

import org.nsu.communication.SerializableSender;
import org.nsu.communication.message.*;
import org.nsu.communication.Sender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Server {

    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static int port = 123;
    private static final Map<String, String> sessionMap = new HashMap<>();
    private static final Map<String, ObjectOutputStream> outputStreamMap = new HashMap<>();
    private static final Map<String, ClientHandler> sessionHandlerMap = new HashMap<>();
    private static final LinkedList<String> chatHistory = new LinkedList<>();
    private static int MAX_HISTORY_SIZE = 100;

    public static void main(String[] args) {

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("server.log");
        } catch (IOException ex) {
            logger.warning(ex.getMessage());
            throw new RuntimeException(ex);
        }

        logger.addHandler(fileHandler);

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.run();
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private static class ClientHandler  {
        private final Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Sender sender;

        public MessageReader messageReader;
        public MessageSender messageSender;

        private final List<String> messageQueue = Collections.synchronizedList(new LinkedList<>());

        private void addToHistory(String message) {
            if (chatHistory.size() > MAX_HISTORY_SIZE) {
                chatHistory.removeFirst();
            }
            chatHistory.add(message);
        }

        public ClientHandler(Socket socket) {

            this.socket = socket;
            try {
                this.in = new ObjectInputStream(socket.getInputStream());
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.sender = new SerializableSender();
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }

        public void run() {

            this.messageReader = new MessageReader();
            this.messageSender = new MessageSender();
            messageReader.start();
            messageSender.start();
        }

        private class MessageReader extends Thread {
            @Override
            public void run() {
                try {
                    while (true) {
                        Message message = sender.getClientMessage(in);

                        switch (message.commandName) {
                            case "login" -> {

                                String clientSessionID = Integer.toString(socket.getPort());
                                sessionMap.put(clientSessionID, ((LoginMessage) message).userName);
                                outputStreamMap.put(clientSessionID, out);
                                sessionHandlerMap.put(clientSessionID, ClientHandler.this);

                                ServerToClientMessage registrationResponse = new ServerToClientMessage("command", clientSessionID, "success", "session", clientSessionID);
                                sender.sendMessage(out, registrationResponse);

                                if (!chatHistory.isEmpty()) {
                                    sender.sendChatHistory(out, clientSessionID, chatHistory);
                                }

                                String messageContent = ((LoginMessage) message).userName + " : " + " connected";
                                messageQueue.add(messageContent);
                                addToHistory(((LoginMessage) message).userName + " connected");

                            }
                            case "message" -> {
                                String senderName = sessionMap.get(message.session);
                                String messageContent = senderName + " : " + ((ClientToServerMessage) message).message;
                                addToHistory(messageContent);
                                messageQueue.add(messageContent);

                            }
                            case "logout" -> {
                                addToHistory(sessionMap.get(message.session) + " disconnected");
                                closeConnection(message.session);
                            }
                            default -> sender.sendUserListResponse(out, sessionMap, message.session);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    logger.warning(e.getMessage());
                }
            }
        }

        private class MessageSender extends Thread {
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (messageQueue) {
                            if (!messageQueue.isEmpty()) {
                                String messageContent = messageQueue.removeFirst();
                                for (String clientSessionID : sessionMap.keySet()) {
                                    sessionHandlerMap.get(clientSessionID).sender.sendMessage(outputStreamMap.get(clientSessionID), new ServerToClientMessage("message", clientSessionID, "message", messageContent, "Server"));
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    logger.warning(e.getMessage());
                }
            }
        }

        private void closeConnection(String clientSessionID) {
            try {
                socket.close();
                String username = sessionMap.get(clientSessionID);
                sessionMap.remove(clientSessionID);
                sendUserLogoutResponse(username);
                ObjectOutputStream remove = outputStreamMap.remove(clientSessionID);
                remove.close();
                messageSender.interrupt();
                messageReader.interrupt();
                sessionHandlerMap.remove(clientSessionID);

            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }

        private void sendUserLogoutResponse(String username) {
            try {
                ServerToClientMessage userLogoutResponse = new ServerToClientMessage("message", null, "message", username + " : " + "disconnected", username);
                for (String id : sessionMap.keySet()) {
                    sessionHandlerMap.get(id).sender.sendMessage(outputStreamMap.get(id), userLogoutResponse);
                }
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }
}