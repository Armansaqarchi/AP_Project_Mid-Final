package messenger.dataBaseOp;

import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.exception.UserNotFoundException;
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


    private User findByConfigUser(String config, String columnName)
            throws SQLException, ClassNotFoundException, IOException, ConfigNotFoundException{
        ResultSet resultSet = findByConfig(config, columnName, "users");
        return createUserFromData(resultSet);
    }


    public User findById(String id) throws SQLException, IOException,
            ClassNotFoundException, ConfigNotFoundException{
        return findByConfigUser(id, "user_id");
    }

    public User findByName(String name) throws SQLException, ClassNotFoundException,
            ConfigNotFoundException, IOException{
        return findByConfigUser(name, "name");
    }

    public User findByEmail(String email) throws SQLException, IOException,
            UserNotFoundException, ClassNotFoundException{
        return findByConfigUser(email, "email");
    }

    public User findByPhoneNumber(String phoneNumber) throws SQLException, ClassNotFoundException,
            UserNotFoundException, IOException{
        return findByConfigUser(phoneNumber, "phone_number");
    }

    public void insertUser(String id, String name, String password, String email, String phoneNumber)
    throws SQLException{

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

    public void insertUser(User user)throws SQLException{
        insertUser(user.getId(), user.getName(), user.getPassword(),
                user.getEmail(), user.getPhoneNumber());
    }



    public void updateProfile(String id, String type, String newValue)
            throws SQLException, ConfigNotFoundException{
        String query = "UPDATE users SET " + type +" = ? where user_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        int res = st.executeUpdate();


        if(res == 0){
            throw new ConfigNotFoundException(id, type, "user");
        }



    }


    public <T> boolean updateList(UpdateType type, String columnName, String id, T t)
            throws SQLException, IOException, ClassNotFoundException, UserNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        Object o = null;

        if(resultSet == null){
            throw new UserNotFoundException(id, columnName);
        }

        while (resultSet.next()) {
            o = byteConvertor(resultSet.getBytes(columnName));
        }
        if (o instanceof LinkedList<?>) {
            targetList = (LinkedList<T>) o;
        }


        switch (type.showValue()) {


            case "Add":
                targetList = addToLists(targetList, t);
                break;


            case "Remove":
                targetList = removeFromList(targetList, t);
                break;

            default:
                return false;


        }


        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE users SET " + columnName + " = ? WHERE user_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, "3");


        pst2.executeUpdate();

        return true;
    }

    public boolean deleteUSerById(String id) throws SQLException, ConfigNotFoundException{
        return deleteById(id, "users", "user_id", "user");
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





    private User createUserFromData(ResultSet resultSet) throws SQLException, IOException,
            ClassNotFoundException {


        String id = resultSet.getString("user_id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");

        byte[] profileImage = resultSet.getBytes("profile_image");
        UserStatus us = UserStatus.getValueFromStatus(resultSet.getString("user_status"));
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
        if((o = byteConvertor(resultSet.getBytes("unread_messages"))) instanceof LinkedList<?>){
            unreadMessages = (LinkedList<UUID>) o;
        }
        else{
            unreadMessages = null;
        }
        if((o = byteConvertor(resultSet.getBytes("friend_requests"))) instanceof LinkedList<?>){
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
