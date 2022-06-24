package messenger.service.model.user;

import messenger.service.model.request.user.FriendReq;
import java.io.Serializable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

public class User implements Serializable
{
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;



    private byte[] profileImage;

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

    //list of friend requests which user has not answered them yet
    private LinkedList<UUID> friendRequests;

    public User(String id, String name, String password, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(String id, String name, String password, String email, String phoneNumber,
                byte[] profileImage, UserStatus userStatus, LinkedList<String> friendList,
                LinkedList<String> blockedUsers, LinkedList<String> privateChats,
                LinkedList<ServerIDs> servers, LinkedList<UUID> unreadMessages,
                LinkedList<UUID> friendRequests) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.userStatus = userStatus;
        this.friendList = friendList;
        this.blockedUsers = blockedUsers;
        this.privateChats = privateChats;
        this.servers = servers;
        this.unreadMessages = unreadMessages;
        this.friendRequests = friendRequests;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImage=" + Arrays.toString(profileImage) +
                ", userStatus=" + userStatus +
                ", friendList=" + friendList +
                ", blockedUsers=" + blockedUsers +
                ", privateChats=" + privateChats +
                ", servers=" + servers +
                ", unreadMessages=" + unreadMessages +
                ", friendRequests=" + friendRequests +
                '}';
    }
}
