package messenger.service.model;

import java.util.LinkedList;
import java.util.UUID;

public class PrivateChat
{
    //id of two users with a '-' between : id1-id2
    private String id;

    private LinkedList<UUID> messages;
}
