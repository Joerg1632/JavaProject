package org.nsu.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class ClientView extends JFrame {

    private final JTextArea chatArea;
    private final JTextField messageField;
    private final String username;

    public ClientView(Consumer<String> messageHandler) {

        this.username = JOptionPane.showInputDialog("Введите ваше имя:");

        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.addActionListener(e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                messageHandler.accept(message);
                if (message.equals("-exit")){
                    dispose();
                }
                messageField.setText("");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                messageHandler.accept("-exit");
                dispose();
                super.windowClosing(e);
            }
        });

        add(messageField, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void addMessage(String message) {
        chatArea.append(message);
    }

    public String getUsername() {
        return username;
    }
}