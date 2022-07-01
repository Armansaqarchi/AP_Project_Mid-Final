package messenger.dataBaseOp;

import model.PrivateChat;
import model.exception.ConfigNotFoundException;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

public class PrivateChatOp extends Op{

    public PrivateChatOp(Connection connection){
        super(connection);
    }

    public PrivateChat findByConfigPrivateChat(String config, String columnName)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return createPrivateChatFromData(findByConfig(config, columnName, "private_chats"));
    }

    public PrivateChat findById(String id)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return findByConfigPrivateChat(id, "id");
    }


    public void insertPrivateMessage(String id)
            throws SQLException, IOException{

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO private_chats VALUES (?, ?)");

        ps.setString(1, id);
        ps.setBytes(2, objectConvertor(new LinkedList<UUID>()));

        ps.executeUpdate();
        ps.close();

        System.out.println("data has been inserted successfully.");
    }

    public boolean deletePrivateChatById(String id)throws SQLException, ConfigNotFoundException {
        return deleteById(id, "private_chats", "id", "privateChat");
    }


    public <T> boolean updatePrivateChat(UpdateType type, String columnName, String id, T t)
            throws SQLException, IOException, ClassNotFoundException, ConfigNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM private_chats WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        if(resultSet == null){
            throw new ConfigNotFoundException(id, columnName, "private chat");
        }

        Object o = null;
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

        String query2 = "UPDATE private_chats SET " + columnName + " = ? WHERE id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }

    public PrivateChat createPrivateChatFromData(ResultSet resultSet)
            throws SQLException, IOException, ClassNotFoundException{

        if(resultSet == null){
            return null;
        }

        String id = resultSet.getString("id");

        LinkedList<UUID> listOfMessages = null;

        Object messages = byteConvertor(resultSet.getBytes("messages"));

        if(messages instanceof LinkedList<?>){
            listOfMessages = (LinkedList<UUID>) messages;
        }

        return new PrivateChat(id, listOfMessages);


    }

    public boolean isExists(String id)
    {
        try
        {
            PrivateChat privateChat = findById(id);
            return true;
        }
        catch (ConfigNotFoundException e)
        {
            return false;
        }
        catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
