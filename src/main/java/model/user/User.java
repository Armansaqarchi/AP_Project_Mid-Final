package model.user;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

/**
 * simulates a user that being used in messenger
 */

public class User implements Serializable
{
    private String id;
    private String name;
    private String password;
    private final String email;
    private final String phoneNumber;



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

    /**
     * users information as a string
     * @return
     */
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

    /**
     * @return user's id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id user's id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getServerIds(){
        ArrayList<String> serverIds = new ArrayList<>();

        for(ServerIDs serverIDs : servers){
            serverIds.add(serverIDs.getId());
        }

        return serverIds;
    }

    /**
     * @return user's name
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password user's name
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return users phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return users profile
     */
    public byte[] getProfileImage() {
        return profileImage;
    }

    /**
     * @return users profile
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * @return friends of user
     */
    public LinkedList<String> getFriendList() {
        return friendList;
    }

    /**
     * @return blocked list of this user
     */
    public LinkedList<String> getBlockedUsers() {
        return blockedUsers;
    }

    /**
     * @return private chats of this user
     */
    public LinkedList<String> getPrivateChats() {
        return privateChats;
    }

    /**
     * @return servers list of user
     */
    public LinkedList<ServerIDs> getServers() {
        return servers;
    }

    /**
     * @return list of unread messages of user
     */
    public LinkedList<UUID> getUnreadMessages() {
        return unreadMessages;
    }

    /**
     * @return list of friend requests sent to this user
     */
    public LinkedList<UUID> getFriendRequests() {
        return friendRequests;
    }
}
