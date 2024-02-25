package com.rikerik.BookWave.Model;
//A chathez tartozó enumok és változók

/**
 * Represents a chat message in the application.
 */
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;

    /**
     * Enum representing the type of the chat message.
     */
    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

    /**
     * Returns the content of the chat message.
     * 
     * @return the content of the chat message
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the chat message.
     * 
     * @param content the content of the chat message
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the sender of the chat message.
     * 
     * @return the sender of the chat message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender of the chat message.
     * 
     * @param sender the sender of the chat message
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Returns the type of the chat message.
     * 
     * @return the type of the chat message
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Sets the type of the chat message.
     * 
     * @param type the type of the chat message
     */
    public void setType(MessageType type) {
        this.type = type;
    }
}
