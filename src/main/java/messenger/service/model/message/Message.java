package messenger.service.model.message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

public abstract class Message implements Serializable
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

}
