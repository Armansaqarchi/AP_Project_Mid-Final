package model.server;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

/**
 * simulates a chatting server of messenger
 * that holds channels to make communication between users
 */
public class Server implements Serializable
{
    private byte[] image;

    private String id;
    private final String ownerId;
    private String name;
    //id of users
    private LinkedList<String> users;
    //list of defined roles (key is the id of user)
    private HashMap<String , Rule> rules;
    //list of channels (key is the name of channel)
    private HashMap<String , UUID> channels;

    public Server(byte[] image, String id, String ownerId, String name,
                  LinkedList<String> users, HashMap<String, Rule> rules,
                  HashMap<String, UUID> channels) {
        this.image = image;
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.users = users;
        this.rules = rules;
        this.channels = channels;
    }

    /**
     * @return servers image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image servers image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * @return servers id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id servers id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the owners id
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @return servers name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name servers name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return servers users
     */
    public LinkedList<String> getUsers() {
        return users;
    }

    /**
     * @param users servers users
     */
    public void setUsers(LinkedList<String> users) {
        this.users = users;
    }

    /**
     * @return rules of users in this server
     */
    public HashMap<String, Rule> getRules() {
        return rules;
    }

    /**
     * @return channels of this server
     */
    public HashMap<String, UUID> getChannels() {
        return channels;
    }

    /**
     * information of server as a string
     * @return
     */
    @Override
    public String toString() {
        return "Server{" +
                "image=" + Arrays.toString(image) +
                ", id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", rules=" + rules +
                ", channels=" + channels +
                '}';
    }
}