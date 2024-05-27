package org.nsu;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class XMLChatServer {
    private static final int PORT = 12345;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        register(selector, serverChannel);
                    }
                    if (key.isReadable()) {
                        handleClientMessage(key);
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        clients.add(new ClientHandler(clientChannel));
        System.out.println("New client connected: " + clientChannel.getRemoteAddress());
    }

    private static void handleClientMessage(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientHandler handler = findClientHandler(clientChannel);
        if (handler != null) {
            handler.readMessage();
        }
    }

    private static ClientHandler findClientHandler(SocketChannel clientChannel) {
        for (ClientHandler handler : clients) {
            if (handler.getChannel().equals(clientChannel)) {
                return handler;
            }
        }
        return null;
    }

    static class ClientHandler {
        private final SocketChannel clientChannel;
        private final ByteBuffer buffer = ByteBuffer.allocate(1024);
        private final Charset charset = StandardCharsets.UTF_8;
        private String userName;

        ClientHandler(SocketChannel clientChannel) {
            this.clientChannel = clientChannel;
        }

        SocketChannel getChannel() {
            return clientChannel;
        }

        void readMessage() {
            try {
                int bytesRead = clientChannel.read(buffer);
                if (bytesRead == -1) {
                    disconnect();
                    return;
                }
                buffer.flip();
                String message = charset.decode(buffer).toString();
                buffer.clear();

                if (userName == null) {
                    handleLogin(message);
                } else {
                    handleClientMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                disconnect();
            }
        }

        private void handleLogin(String message) {
            ChatMessage chatMessage = ChatMessage.fromXML(message);
            if (chatMessage != null && "login".equals(chatMessage.getType())) {
                userName = chatMessage.getUserName();
                broadcast(new ChatMessage("userlogin", null, userName).toXML());
            } else {
                sendErrorResponse("Invalid login command");
            }
        }

        private void handleClientMessage(String message) {
            ChatMessage chatMessage = ChatMessage.fromXML(message);
            if (chatMessage != null && "message".equals(chatMessage.getType())) {
                broadcast(new ChatMessage("message", chatMessage.getMessage(), userName).toXML());
            } else {
                sendErrorResponse("Invalid message command");
            }
        }

        void broadcast(String message) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }

        void sendMessage(String message) {
            try {
                ByteBuffer msgBuffer = ByteBuffer.wrap(message.getBytes(charset));
                clientChannel.write(msgBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void sendErrorResponse(String reason) {
            sendMessage(new ChatMessage("error", reason, null).toXML());
        }

        void disconnect() {
            try {
                clients.remove(this);
                clientChannel.close();
                System.out.println("Client disconnected: " + userName);
                if (userName != null) {
                    broadcast(new ChatMessage("userlogout", null, userName).toXML());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
