package model.message;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

public class FileMessage extends Message
{
    private final String fileName;

    private byte[] content;

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileName()
    {
        return fileName;
    }

    @Override
    public String toString() {
        return "\n" + getSenderId() + " : \n" +
                "File message" + "            Reactions : " + showReactions();
    }
}
