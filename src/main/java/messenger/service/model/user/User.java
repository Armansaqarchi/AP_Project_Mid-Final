package messenger.service.model.user;

import java.util.LinkedList;
import java.util.UUID;

public class User
{
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String profileImage;

    private UserStatus userStatus;

    //list of friend ids
    private LinkedList<String> friendList;
    //list of blocked user ids
    private LinkedList<String> blockedUsers;
    //list of private chat ids
    private LinkedList<String> privateChats;
    //list of servers and their channels
    private LinkedList<ServerIDs> servers;

    private LinkedList<UUID> unreadMessages;

    //list of friends requests
}
