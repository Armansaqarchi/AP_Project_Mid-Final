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
    }

    public abstract String showMessage();


    public String showReactions(){
        String reactionList = "";
        for(MessageReaction mr : reactions){
            reactionList += mr.toString();
        }

        return reactionList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LinkedList<MessageReaction> getReactions() {
        return reactions;
    }

    public void setReactions(LinkedList<MessageReaction> reactions) {
        this.reactions = reactions;
    }

    public abstract Object getContent();

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", reactions=" + reactions +
                '}';
    }

}
