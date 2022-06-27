package messenger.service.model.message;

import messenger.service.model.Transferable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

public abstract class Message implements Transferable , Serializable
{
    private UUID id;
    private String senderId;

    //id of the receiver
    //for private chat : id of user
    //for server : "server's id - channel name"
    private String receiverId;
    private MessageType type;
    private LocalDateTime date;

    //list of reactions to this message
    private LinkedList<MessageReaction> reactions;

    public MessageType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LinkedList<MessageReaction> getReactions() {
        return reactions;
    }

    protected String showMessageReactions(){
        String showReacts = "";
        for(MessageReaction i : reactions){
            showReacts += (i.getSenderId() + " : " + i.getReaction() + " ");
        }

        return showReacts;
    }

    public abstract String showMessage();



}
