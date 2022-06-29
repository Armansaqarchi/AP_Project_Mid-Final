package model.server;

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
    }

    public UUID getId()
    {
        return id;
    }

    public LinkedList<String> getUsers()
    {
        return users;
    }
}
