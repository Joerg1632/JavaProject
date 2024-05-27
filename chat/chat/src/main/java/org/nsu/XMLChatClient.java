package org.nsu;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import javax.swing.*;

public class XMLChatClient {
    private String userName;
    private SocketChannel clientChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Charset charset = StandardCharsets.UTF_8;
    private JFrame frame;
    private JTextArea messageArea;
    private JTextField textField;

    public static void main(String[] args) {
        XMLChatClient client = new XMLChatClient();
        client.start();
    }

    public void start() {
        frame = new JFrame("Chat Client");
        textField = new JTextField(50);
        messageArea = new JTextArea(16, 50);
        messageArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.pack();

        textField.addActionListener(e -> {
            String message = textField.getText();
            if (!message.isEmpty()) {
                sendMessage(new ChatMessage("message", message, userName).toXML());
                textField.setText("");
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        userName = JOptionPane.showInputDialog(frame, "Enter your name:", "User Login", JOptionPane.PLAIN_MESSAGE);
        if (userName != null && !userName.isEmpty()) {
            connectToServer();
        } else {
            System.exit(0);
        }
    }

    private void connectToServer() {
        try {
            clientChannel = SocketChannel.open(new InetSocketAddress("localhost", 12345));
            clientChannel.configureBlocking(false);
            sendMessage(new ChatMessage("login", null, userName).toXML());

            new Thread(this::readMessages).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            ByteBuffer msgBuffer = ByteBuffer.wrap(message.getBytes(charset));
            clientChannel.write(msgBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() {
        try {
            while (true) {
                buffer.clear();
                int bytesRead = clientChannel.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    String message = charset.decode(buffer).toString();
                    buffer.clear();
                    processMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(String message) {
        ChatMessage chatMessage = ChatMessage.fromXML(message);
        if (chatMessage != null) {
            switch (chatMessage.getType()) {
                case "message":
                    messageArea.append(chatMessage.getUserName() + ": " + chatMessage.getMessage() + "\n");
                    break;
                case "userlogin":
                    messageArea.append("User " + chatMessage.getUserName() + " has joined.\n");
                    break;
                case "userlogout":
                    messageArea.append("User " + chatMessage.getUserName() + " has left.\n");
                    break;
                case "error":
                    messageArea.append("Error: " + chatMessage.getMessage() + "\n");
                    break;
            }
        } else {
            messageArea.append("Received invalid message format.\n");
        }
    }
}
