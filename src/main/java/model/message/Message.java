package model.message;


import model.Transferable;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/**
 * parent class of messages that being sent in messenger
 */

public abstract class Message implements Transferable

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
    private LinkedList <MessageReaction> reactions;

    public Message(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date, LinkedList<MessageReaction> reactions) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.date = date;
        this.reactions = reactions;
    }

    public Message(UUID id, String senderId, String receiverId, MessageType type, LocalDateTime date) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.date = date;
        reactions = new LinkedList<>();
    }

    /**
     * @return reactions to this message in a string
     */
    public String showReactions(){
        String reactionList = "";
        for(MessageReaction mr : reactions){
            reactionList += mr.toString();
        }

        return reactionList;
    }

    /**
     * @return messages uuid
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id set the messages id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return messages sender
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * @param senderId set the messages id
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the receiver id
     */
    public String getReceiverId() {
        return receiverId;
    }

    /***
     * @param receiverId the receiver id
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * @return messages type
     */
    public MessageType getType() {
        return type;
    }

    /**
     * @param type messages type
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * @return date of creating message
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date date of creating message
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * @return reaction's to message
     */
    public LinkedList<MessageReaction> getReactions() {
        return reactions;
    }

    /**
     * @param reactions reaction's to message
     */
    public void setReactions(LinkedList<MessageReaction> reactions) {
        this.reactions = reactions;
    }

    /**
     * @return messages object
     */
    public abstract Object getContent();

    /**
     * checks equality of inputted object with this using its id
     * @param o inputted object
     * @return equals or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId().equals(message.getId());
    }
}
