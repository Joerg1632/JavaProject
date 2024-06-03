package org.nsu.communication;

import org.nsu.communication.message.Message;
import org.nsu.communication.message.ServerToClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Map;

public interface Sender {
    void sendLogoutCommand(ObjectOutputStream out, String sessionID) throws IOException;

    void requestUserList(ObjectOutputStream out, String sessionID) throws IOException;

    void sendMessageToServer(ObjectOutputStream out, String message, String sessionID) throws IOException;

    void sendMessage(ObjectOutputStream out, ServerToClientMessage registrationResponse)throws IOException;

    void sendChatHistory(ObjectOutputStream out, String sessionID, LinkedList<String> chatHistory)throws IOException;

    void sendUserListResponse(ObjectOutputStream objectOutputStream, Map<String, String> sessionMap, String session)throws IOException, ClassNotFoundException;

    String getServerMessage(ObjectInputStream in) throws ClassNotFoundException, IOException;

    String tryToConnect(ObjectOutputStream out, ObjectInputStream in, String username) throws IOException, ClassNotFoundException;

    Message getClientMessage(ObjectInputStream in) throws IOException, ClassNotFoundException ;
}
