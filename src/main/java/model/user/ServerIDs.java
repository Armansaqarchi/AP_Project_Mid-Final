package model.user;

import java.io.Serializable;
import java.util.LinkedList;

/***
 * holds a server's id with list of its channels
 */
public class ServerIDs implements Serializable
{
    //the servers id
    private final String id;

    //name of channels that the user is a member of them
    private final LinkedList<String> channels;

    public ServerIDs(String id, LinkedList<String> channels)
    {
        this.id = id;
        this.channels = channels;
    }

    /**
     * @return the servers id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return list of server's channels
     */
    public LinkedList<String> getChannels() {
        return channels;
    }

    /**
     * checks that inputted object is equals with this or not
     * using its server id
     * @param o the inputted object
     * @return equals or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerIDs)) return false;

        return ((ServerIDs) o).getId().equals(id);
    }
}
