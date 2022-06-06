package messenger.service.model.server;

import java.util.LinkedList;

public abstract class Channel
{
    private String id;
    private String name;
    //id of users
    private LinkedList<String> users;

}
