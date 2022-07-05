package model;

import java.util.LinkedList;
import java.util.UUID;

/**
 * simulates a chat between two users in messenger
 */

public class PrivateChat
{
    //id of two users with a '-' between : id1-id2
    //first id is less than second
    private final String id;
    private final LinkedList<UUID> messages;


    public PrivateChat(String id, LinkedList<UUID> messages) {
        this.id = id;
        this.messages = messages;
    }

    /**
     * @return private chats messages
     */
    public LinkedList<UUID> getMessages()
    {
        return messages;
    }
}
