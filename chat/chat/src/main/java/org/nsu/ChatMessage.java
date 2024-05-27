package org.nsu;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ChatMessage implements Serializable {
    private String type;
    private String message;
    private String userName;

    public ChatMessage(String type, String message, String userName) {
        this.type = type;
        this.message = message;
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String toXML() {
        return String.format("<message><type>%s</type><userName>%s</userName><message>%s</message></message>", type, userName, message);
    }

    public static ChatMessage fromXML(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            Element root = document.getDocumentElement();

            String type = getTextContent(root, "type");
            String userName = getTextContent(root, "userName");
            String message = getTextContent(root, "message");

            return new ChatMessage(type, message, userName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getTextContent(Element element, String tagName) {
        Node node = element.getElementsByTagName(tagName).item(0);
        return node != null ? node.getTextContent() : null;
    }
}
