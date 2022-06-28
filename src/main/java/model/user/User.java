package model.user;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public LinkedList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(LinkedList<String> friendList) {
        this.friendList = friendList;
    }

    public LinkedList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(LinkedList<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public LinkedList<String> getPrivateChats() {
        return privateChats;
    }

    public void setPrivateChats(LinkedList<String> privateChats) {
        this.privateChats = privateChats;
    }

    public LinkedList<ServerIDs> getServers() {
        return servers;
    }

    public void setServers(LinkedList<ServerIDs> servers) {
        this.servers = servers;
    }

    public LinkedList<UUID> getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(LinkedList<UUID> unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public LinkedList<UUID> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(LinkedList<UUID> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
