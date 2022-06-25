package messenger.service.model.server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public abstract class Channel implements Serializable
{
    private UUID id;
    private String name;

    private ChannelType type;
    //id of users
    private LinkedList<String> users;

}
