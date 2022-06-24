package messenger.dataBaseOp;


import messenger.service.model.message.*;

import java.sql.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public abstract class MessageOp extends Op {


    public MessageOp(Connection connection){
        super(connection);
    }


    public Message findByConfigMessage(String config, String columnName)
    throws IOException, SQLException, ClassNotFoundException{
        return createMessageFromData(findByConfig(config, columnName, "message"));
    }

    public  Message findById(String id)
    throws IOException, SQLException, ClassNotFoundException{
        return findByConfigMessage(id, "message_id");
    }

    public  Message findBySenderId(String senderId)
    throws IOException, ClassNotFoundException, SQLException{
        return findByConfigMessage(senderId, "sender_id");
    }

    public  Message findByReceiverId(String receiverId)
    throws IOException, ClassNotFoundException, SQLException{
        return findByConfigMessage(receiverId, "receiver_id");
    }

    public  void insertMessage(String messageId, String senderId, String receiverId, MessageType type, LocalDateTime date, Object content)
    throws SQLException, IOException{

        PreparedStatement pst = connection.prepareStatement(
                "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        pst.setString(1, messageId);
        pst.setString(2, senderId);
        pst.setString(3, receiverId);
        pst.setString(4, MessageType.getValueFromName(type));
        pst.setDate(5, new java.sql.Date(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()).getTime()));
        pst.setNull(6, Types.BINARY);
        if(type.name().equals("FILE_PRIVATE") || type.name().equals("FILE_SERVER")){
            pst.setBytes(7, (byte[]) content);
        }
        else if(type.name().equals("TEXT_PRIVATE") || type.name().equals("TEXT_SERVER")){
            pst.setBytes(7, objectConvertor(content));
        }

        pst.executeUpdate();
        pst.close();


        System.out.println("data has been inserted successfully.");

    }


    public void insertMessage(Message message){
        insertMessage(message.getId().toString(), message.getSenderId(), message.getReceiverId());
    }

    public void updateMessage(String id, String type, String newValue)throws SQLException{
        String query = "UPDATE message SET " + type +" = ? where user_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        st.executeUpdate();
    }

    public  boolean updateReactions(UpdateType type ,String columnName, String id, Reaction reaction)
    throws IOException, ClassNotFoundException, SQLException{

        LinkedList<Reaction> targetList = null;

        String query = "SELECT * FROM message WHERE user_id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        Object o = null;
        while(resultSet.next()) {
            o = byteConvertor(resultSet.getBytes(columnName));
        }
        if(o instanceof LinkedList<?>){
            targetList = (LinkedList<Reaction>) o;
        }

        targetList = updateByType(type, targetList, reaction);

        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE message SET " + columnName +" = ? WHERE user_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }

    public void deleteMessageById(String id) throws SQLException{
        deleteById(id, "message");
    }

    private Message createMessageFromData(ResultSet resultSet)
    throws SQLException, IOException, ClassNotFoundException {

        String messageId = resultSet.getString("message_id");
        String senderId = resultSet.getString("sender_id");
        String receiverId = resultSet.getString("receiver_id");
        String type = resultSet.getString("type");
        LocalDateTime date =
                convertDateToLocalDatetime(resultSet.getDate("date"));


        LinkedList<Reaction> reactions = null;

        Object o;

        if ((o = byteConvertor(resultSet.getBytes("reaction"))) instanceof LinkedList<?>) {
            reactions = (LinkedList<Reaction>) o;
        }



        if(type.equals("textPrivate") || type.equals("textServer")){
            Object content = byteConvertor(resultSet.getBytes("content"));
            if(content instanceof LinkedList<?>){
                return new TextMessage(UUID.fromString(messageId), senderId, receiverId,
                        MessageType.getNameFromValue(type), date, reactions, (String)content);
            }
        }
        else if (type.equals("filePrivate") || type.equals("fileServer")){
            byte[] content = resultSet.getBytes("content");
            return new FileMessage(UUID.fromString(messageId), senderId, receiverId,
                    MessageType.getNameFromValue(type), date, reactions, content);
        }

        return null;

    }



    private LocalDateTime convertDateToLocalDatetime(Date date){
        LocalDateTime localDateTime = date.toInstant().
                atZone(ZoneId.systemDefault()).toLocalDateTime();


        return localDateTime;
    }
}
