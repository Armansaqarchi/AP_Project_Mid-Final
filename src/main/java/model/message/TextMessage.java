package model.message;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

/**
 * this class simulates a text message that being sent in messenger
 */
public class TextMessage extends Message
{
    private String content;


    public TextMessage(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, LinkedList<MessageReaction> reactions, String content) {
        super(id, senderId, receiverId, type, date, reactions);
        this.content = content;
    }

    public TextMessage(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, String content) {
        super(id, senderId, receiverId, type, date);

        this.content = content;
    }

    /**
     * @return messages content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content messages content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return messages information as a string
     */
    public String toString()
    {
        return "\033[0;34m\n" + getSenderId() + " : \n" +
                content + "\nReactions : " + showReactions() +
                "id : " + getId() +"\033[0m";
    }
}
