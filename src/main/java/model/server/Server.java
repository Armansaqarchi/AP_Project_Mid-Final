package model.server;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class Server implements Serializable
{
    private byte[] image;

    private String id;
    private String ownerId;
    private String name;
    //id of users
    private LinkedList<String> users;
    //list of defined roles (key is the id of user)
    private HashMap<String , Rule> rules;
    //list of channels (key is the name of channel)
    private HashMap<String , UUID> channels;


    public Server(String id, String ownerId, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

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


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<String> users) {
        this.users = users;
    }

    public HashMap<String, Rule> getRules() {
        return rules;
    }

    public void setRules(HashMap<String, Rule> rules) {
        this.rules = rules;
    }

    public HashMap<String, UUID> getChannels() {
        return channels;
    }

    public void setChannels(HashMap<String, UUID> channels) {
        this.channels = channels;
    }


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