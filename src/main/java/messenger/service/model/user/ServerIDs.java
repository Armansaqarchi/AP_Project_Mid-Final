package service.model.user;

//this class is only used to save servers id and
// its channels ids in a user object
import java.util.LinkedList;

public class ServerIDs
{
    //the servers id
    private String id;

    //ids of channels that the user is a member of them
    LinkedList<String> channels;
}
