package model.message;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

/**
 * this class simulates a file message that being sent in messenger
 */
public class FileMessage extends Message
{
    private final String fileName;

    private final byte[] content;

    public FileMessage(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, String fileName , byte[] content) {
        super(id, senderId, receiverId, type, date);
        this.fileName = fileName;
        this.content = content;
    }

    public FileMessage(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, LinkedList<MessageReaction> reactions, String fileName, byte[] content) {
        super(id, senderId, receiverId, type, date, reactions);
        this.fileName = fileName;
        this.content = content;
    }

    /**
     * @return the file as byte array
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @return the files name
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @return information of message in a string
     */
    @Override
    public String toString() {
        return "\033[0;34m\n" + getSenderId() + " : \n" +
                "File message" + "\nReactions : " + showReactions() +
                "id : " + getId() +"\033[0m";
    }
}
