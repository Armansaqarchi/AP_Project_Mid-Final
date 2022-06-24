package messenger.service.model.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class Server
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

}