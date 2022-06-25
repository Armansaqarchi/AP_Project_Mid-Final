package messenger.dataBaseOp;

import messenger.service.model.PrivateChat;
import messenger.service.model.request.user.FriendReq;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

public class PrivateChatOp extends Op{

    public PrivateChatOp(Connection connection){
        super(connection);
    }

    public PrivateChat findByConfigPrivateChat(String config, String columnName)
            throws IOException, SQLException, ClassNotFoundException{
        return createPrivateChatFromData(findByConfig(config, columnName, "private_chats"));
    }

    public  PrivateChat findById(String id)
            throws IOException, SQLException, ClassNotFoundException{
        return findByConfigPrivateChat(id, "id");
    }


    public void insertPrivateMessage(UUID id)
            throws SQLException{

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO friend_requests VALUES (?, ?)");

        ps.setString(1, id.toString());
        ps.setNull(2, Types.BINARY);

        ps.executeUpdate();
        ps.close();

        System.out.println("data has been inserted successfully.");
    }


    public <T> boolean updateMessages(UpdateType type, String columnName, String id, T t)
            throws SQLException, IOException, ClassNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM private_chats WHERE id = ?";
        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

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

        String query2 = "UPDATE users SET " + columnName + " = ? WHERE user_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, "3");


        pst2.executeUpdate();

        return true;
    }

    public PrivateChat createPrivateChatFromData(ResultSet resultSet)
            throws SQLException, IOException, ClassNotFoundException{

        if(resultSet == null){
            return null;
        }

        String id = resultSet.getString("id");

        Object messages = byteConvertor(resultSet.getBytes("messages"));

        if(messages instanceof LinkedList<?>){
            return new PrivateChat(id, (LinkedList<UUID>) messages);
        }


        return null;

    }


}
