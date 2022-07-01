package model.user;

//this class is only used to save servers id and
// its channels ids in a user object
import java.io.Serializable;
import java.util.LinkedList;

public class ServerIDs implements Serializable
{
    //the servers id
    private String id;

    //name of channels that the user is a member of them
    LinkedList<String> channels;

    public ServerIDs(String id, LinkedList<String> channels)
    {
        this.id = id;
        this.channels = channels;
    }

    public String getId()
    {
        return id;
    }

    public LinkedList<String> getChannels() {
        return channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerIDs)) return false;

        return ((ServerIDs) o).getId().equals(id);
    }
}
