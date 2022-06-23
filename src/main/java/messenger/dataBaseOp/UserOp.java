package messenger.dataBaseOp;

import messenger.service.model.user.ServerIDs;
import messenger.service.model.user.User;
import messenger.service.model.user.UserStatus;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;



public class UserOp extends Op{


    public UserOp(Connection connection){
        super(connection);

    }

    public User findById(String id) throws SQLException{
        String query = "SELECT * WHERE user_id = " + id;

        PreparedStatement pStatement = connection.prepareStatement(query);
        ResultSet resultSet = pStatement.executeQuery(query);

        if(resultSet.next()){
            if(resultSet.getObject(0) instanceof User){
                return (User)resultSet.getObject(0);
            }
        }


        return null;
    }

    public User findByName(String name) throws SQLException{
        String query = "SELECT * FROM users WHERE name = 'arman'";

        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){
            System.out.println(resultSet.getString("password"));
            try {
                return (User)createUserFromData(resultSet);
            }
            catch(SQLException | ClassNotFoundException | IOException e){
                e.printStackTrace();
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
        System.out.println(connection);

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, password);
        ps.setString(4, email);
        ps.setString(5, phoneNumber);
        ps.setObject(6, Types.BINARY);
        ps.setString(7, "Online");
        ps.setNull(8, Types.BINARY);
        ps.setNull(9, Types.BINARY);
        ps.setNull(10, Types.BINARY);
        ps.setNull(11, Types.BINARY);
        ps.setNull(12, Types.BINARY);
        ps.setNull(13, Types.BINARY);;


        ps.executeUpdate();
        ps.close();

        System.out.println("data has been inserted successfully.");

    }

    private byte[] objectConvertor(Object o) throws IOException{

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);

        out.writeObject(o);

        return bout.toByteArray();
    }

    public void updateProfile(String id, String type, String newValue)throws SQLException{
        String query = "UPDATE users SET ? = ? where id = ?";

        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, type);
        st.setString(2, newValue);
        st.setString(3, id);


        st.executeUpdate();

    }

    public <T> void updateLists(String columnName, String id, T t)throws SQLException{

        String query = "SELECT * FROM users where id = ?";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();

        


        //need an exception to handle if type of t matches type of elements of list.

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
        UserStatus us = getNameFromValue(resultSet.getString("user_status"));
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


    private UserStatus getNameFromValue(String value){
        switch(value){
            case "Online":
                return UserStatus.ONLINE;

            case "Offline":
                return UserStatus.OFFLINE;

            case "Idle":
                return UserStatus.IDLE;

            case "Do not disturb":
                return UserStatus.DO_NOT_DISTURB;

            case "Invisible":
                return UserStatus.INVISIBLE;

        }

        return null;
    }









}
