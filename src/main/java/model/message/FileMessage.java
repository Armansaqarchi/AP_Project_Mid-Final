package model.message;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

public class FileMessage extends Message
{
    private byte[] content;

    public FileMessage(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, byte[] content) {
        super(id, senderId, receiverId, type, date);
        this.content = content;
    }

    public FileMessage(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, LinkedList<MessageReaction> reactions, byte[] content) {
        super(id, senderId, receiverId, type, date, reactions);
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String showMessage() {
        return "\n" + getSenderId() + " : \n" +
                "File message" + "            Reactions : " + showReactions();
    }
}
