package model.server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

/**
 * simulates a channel that is created in server
 */
public abstract class Channel implements Serializable
{
    private final UUID id;
    private final String name;

    private final ChannelType type;
    //id of users
    private final LinkedList<String> users;

    public Channel(UUID id, String name, ChannelType type, LinkedList<String> users) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.users = users;
    }

    public Channel(UUID id, String name, ChannelType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        users = new LinkedList<>();
    }

    /**
     * @return channels id
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * @return channels name
     */
    public String getName() {
        return name;
    }

    /**
     * @return users of this channel
     */
    public LinkedList<String> getUsers()
    {
        return users;
    }
}
