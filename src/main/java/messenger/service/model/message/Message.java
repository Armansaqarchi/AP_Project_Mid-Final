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


    public Message(UUID id, String senderId, String receiverId,
                   MessageType type, LocalDateTime date,
                   LinkedList<MessageReaction> reactions) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.date = date;
        this.reactions = reactions;
    }

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
