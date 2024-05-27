package org.nsu;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import javax.swing.*;

public class ChatClient {
    private String userName;
    private SocketChannel clientChannel;
    private final ByteBuffer buffer = ByteBuffer.allocate(256);
    private final Charset charset = StandardCharsets.UTF_8;
    private JTextArea messageArea;
    private JTextField textField;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }

    public void start() {
        JFrame frame = new JFrame("Chat Client");
        textField = new JTextField(50);
        messageArea = new JTextArea(16, 50);
        messageArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.pack();

        textField.addActionListener(e -> {
            String message = textField.getText();
            if (!message.isEmpty()) {
                sendMessage(message);
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
            sendMessage(userName);

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
                    messageArea.append(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}