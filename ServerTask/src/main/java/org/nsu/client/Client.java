package org.nsu.client;

import org.nsu.server.Server;
import org.nsu.communication.Sender;
import org.nsu.communication.SerializableSender;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {

    private Sender sender;
    private Socket socketToServer;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String username;
    private String sessionID;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final ClientGUI clientGUI;

    public Client() {
        this.clientGUI = new ClientGUI(message -> {
            if (message.equals("-exit")) {
                try {
                    sender.sendLogoutCommand(out, sessionID);
                } catch (IOException e) {
                    logger.warning(e.getMessage());
                } finally {
                    closeConnection();
                }
            } else if (message.equals("-list")) {
                try {
                    sender.requestUserList(out, sessionID);
                } catch (IOException e) {
                    logger.warning(e.getMessage());
                }
            } else {
                try {
                    sender.sendMessageToServer(out, message, sessionID);
                } catch (IOException e) {
                    logger.warning(e.getMessage());
                }
            }
        });

        this.username = clientGUI.getUsername();

        try {
            tryToConnect("localhost", 123);
        } catch (IOException | ClassNotFoundException e) {
            logger.warning(e.getMessage());
        }

        new Thread(() -> {
            while (!socketToServer.isClosed()) {
                try {
                    String serverMessage = sender.getServerMessage(in);
                    if (serverMessage != null) {
                        clientGUI.addMessage(serverMessage);
                    }
                } catch (ClassNotFoundException | IOException e) {
                    logger.warning(e.getMessage());
                    closeConnection();
                }
            }
        }).start();
    }

    private void tryToConnect(String host, int port) throws IOException, ClassNotFoundException {
        socketToServer = new Socket(host, port);

        this.out = new ObjectOutputStream(socketToServer.getOutputStream());
        this.in = new ObjectInputStream(socketToServer.getInputStream());

        this.sender = new SerializableSender();

        sessionID = sender.tryToConnect(out, in, username);
    }

    private void closeConnection() {
        try {
            if (socketToServer != null && !socketToServer.isClosed()) {
                socketToServer.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}

