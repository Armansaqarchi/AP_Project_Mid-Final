package messenger.service.model;

import java.util.LinkedList;
import java.util.UUID;

public class PrivateChat
{
    //id of two users with a '-' between : id1-id2
    //first id is less than second
    private String id;
    private LinkedList<UUID> messages;


    public PrivateChat(String id, LinkedList<UUID> messages) {
        this.id = id;
        this.messages = messages;
    }

    public String GetId() {
        return id;
    }

    public LinkedList<UUID> getMessages()
    {
        return messages;
    }
}
