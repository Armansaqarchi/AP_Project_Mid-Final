package messenger.dataBaseOp;

import messenger.service.model.user.ServerIDs;
import messenger.service.model.user.User;
import messenger.service.model.user.UserStatus;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;



public class UserOp extends Op{

    private Connection connection;


    public UserOp(Connection connection){
        super(connection);
    }

    public User findById(String id) throws SQLException{
        String query = "SELECT * WHERE USer_id = " + id;

        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){
            if(resultSet.getObject(0) instanceof User){
                return (User)resultSet.getObject(0);
            }
        }


        return null;
    }

    public User findByName(String name) throws SQLException{
        String query = "SELECT * WHERE user_id = " + name;

        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){
            if(resultSet.getObject(0) instanceof User){
                return (User)resultSet.getObject(0);
            }
        }


        return null;
    }

    public User findByEmail(String email) throws SQLException{
        String query = "SELECT * WHERE user_id = " + email;

        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){
            if(resultSet.getObject(0) instanceof User){
                return (User)resultSet.getObject(0);
            }
        }


        return null;
    }

    public User findByPhoneNumber(String phoneNumber) throws SQLException{
        String query = "SELECT * WHERE user_id = " + phoneNumber;

        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){
            if(resultSet.getObject(0) instanceof User){
                return (User)resultSet.getObject(0);
            }
        }


        return null;
    }

    public void insertUser(String id, String name, String password, String email, String phoneNumber)
    throws SQLException{
        String query = String.format("insert into users values ( %s, %s', %s, %s, %s)",
                id, name, password, email, phoneNumber);
        statement.executeUpdate(query);


    }

    public boolean updateUser(String type, String newValue, String id) throws SQLException{
        String query = "UPDATE users" +
                "SET " + type + " = " + newValue +
                "WHERE user_id = " + id;

        int situation = statement.executeUpdate(query);

        if(situation == 2){
            return false;
        }


        return true;


    }

    public HashMap<String, String> findByUserStatus(String status) throws SQLException{
        String query = "SELECT * FROM user WHERE user_status = " + status;

        ResultSet resultSet = statement.executeQuery(query);
         HashMap<String, String> users = new HashMap<>();

        while(resultSet.next()){

            users.put(resultSet.getString("user_id"), resultSet.getString("name"));

        }


        return users;
    }

    public boolean deleteUserById(String id) throws SQLException{

        String query = "DELETE FROM users WHERE user_id = " + id;

        int affectedRows = statement.executeUpdate(query);

        if(affectedRows == 0) return false;

        return true;
    }



    private User createUserFromData(ResultSet resultSet) throws SQLException, IOException,
            ClassNotFoundException {

        String id = resultSet.getString("user_id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");

        byte[] profileImage = resultSet.getBytes("profile_image");
        UserStatus us = UserStatus.valueOf(resultSet.getString("user_status"));
        LinkedList<String> friendList;
        LinkedList<String> blockedUsers;
        LinkedList<String> privateChats;
        LinkedList<ServerIDs> servers;
        LinkedList<UUID> unreadMessages;
        LinkedList<UUID> friendRequests;

        Object o;

        if((o = byteConvertor(resultSet.getBytes("friend_list"))) instanceof LinkedList<?>){
            friendList = (LinkedList<String>) o;
        }
        else{
            friendList = null;
        }
        if((o = byteConvertor(resultSet.getBytes("blocked_users"))) instanceof LinkedList<?>){
            blockedUsers = (LinkedList<String>) o;
        }
        else{
            blockedUsers = null;
        }
        if((o = byteConvertor(resultSet.getBytes("private_chats"))) instanceof LinkedList<?>){
            privateChats = (LinkedList<String>) o;
        }
        else{
            privateChats = null;
        }
        if((o = byteConvertor(resultSet.getBytes("servers"))) instanceof LinkedList<?>){
            servers = (LinkedList<ServerIDs>) o;
        }
        else{
            servers = null;
        }
        if((o = byteConvertor(resultSet.getBytes("friend_list"))) instanceof LinkedList<?>){
            unreadMessages = (LinkedList<UUID>) o;
        }
        else{
            unreadMessages = null;
        }
        if((o = byteConvertor(resultSet.getBytes("friend_list"))) instanceof LinkedList<?>){
            friendRequests = (LinkedList<UUID>) o;
        }
        else{
            friendRequests = null;
        }

        return new User(id, name, password, email, phoneNumber,
                profileImage, us, friendList, blockedUsers, privateChats, servers,
                unreadMessages, friendRequests);
    }









}
