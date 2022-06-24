package messenger.dataBaseOp;

import messenger.service.model.user.ServerIDs;
import messenger.service.model.user.User;
import messenger.service.model.user.UserStatus;
import org.springframework.util.SerializationUtils;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;



public class UserOp extends Op{


    public UserOp(Connection connection){
        super(connection);

    }


    private User findByConfig(String config, String columnName)
            throws SQLException, ClassNotFoundException, IOException{
        String query = "SELECT * FROM users WHERE " + columnName + " = ?";


        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, config);
        ResultSet resultSet = pStatement.executeQuery();

        if(resultSet.next()){
            return createUserFromData(resultSet);
        }


        return null;
    }


    public User findById(String id) throws SQLException, IOException, ClassNotFoundException{
        return findByConfig(id, "user_id");
    }

    public User findByName(String name) throws SQLException, ClassNotFoundException, IOException{
        return findByConfig(name, "name");
    }

    public User findByEmail(String email) throws SQLException, IOException, ClassNotFoundException{
        return findByConfig(email, "email");
    }

    public User findByPhoneNumber(String phoneNumber) throws SQLException, ClassNotFoundException, IOException{
        return findByConfig(phoneNumber, "phone_number");
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
        ps.setNull(6, Types.BINARY);
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



    public void updateProfile(String id, String type, String newValue)throws SQLException{
        String query = "UPDATE users SET " + type +" = ? where user_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        st.executeUpdate();

    }

    public <T> boolean updateList(UpdateType type, String columnName, String id, T t)
            throws SQLException, IOException, ClassNotFoundException{

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        Object o = null;
        while(resultSet.next()) {
            o = byteConvertor(resultSet.getBytes(columnName));
        }
        if(o instanceof LinkedList<?>){
            targetList = (LinkedList<T>) o;
        }


        switch(type.showValue()){


            case "Add" :
                targetList =  addToLists(targetList, t);
                break;


            case "Remove" :
                targetList = removeFromList(targetList, t);
                break;

            default:
                return false;


        }



        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE users SET " + columnName +" = ? WHERE user_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, "3");


        pst2.executeUpdate();

        return true;
    }

    private <T> LinkedList<T> addToLists(LinkedList<T> list, T t){


        if(list == null){
            list = new LinkedList<>();
        }

        list.add(t);
        return list;
    }

    private <T> LinkedList<T> removeFromList(LinkedList<T> list, T t){
        list.remove(t);
        return list;
    }


    public HashMap<String, String> findByUserStatus(String status) throws SQLException{
        String query = "SELECT * FROM users WHERE user_status = ?";

        PreparedStatement pst2 = connection.prepareStatement(query);
        pst2.setString(1, status);
        ResultSet resultSet = pst2.executeQuery();
         HashMap<String, String> users = new HashMap<>();

        while(resultSet.next()){

            users.put(resultSet.getString("user_id"), resultSet.getString("name"));

        }


        return users;
    }

    public boolean deleteUserById(String id) throws SQLException{

        String query = "DELETE FROM users WHERE user_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query);
        pst2.setString(1, id);
        int affectedRows = pst2.executeUpdate();

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
