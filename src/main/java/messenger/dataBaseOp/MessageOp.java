package messenger.dataBaseOp;


import model.exception.ConfigNotFoundException;
import model.message.*;

import java.sql.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class MessageOp extends Op {


    public MessageOp(Connection connection){
        super(connection);
    }


    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : findByConfigChannel
     * @author Arman sagharchi
     */
    public Message findByConfigMessage(String config, String columnName)
    throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return createMessageFromData(findByConfig(config, columnName, "message"));
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : findById
     * @author Arman sagharchi
     */
    public  Message findById(String id)
    throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return findByConfigMessage(id, "message_id");
    }

    /**
     * same as previous methods
     * passes the sender id as a config
     * @param senderId, message sender id
     * @return the message if it's found by sender id
     * @see ChannelOp, method : findById
     */
    public  Message findBySenderId(String senderId)
    throws IOException, ClassNotFoundException, SQLException, ConfigNotFoundException{
        return findByConfigMessage(senderId, "sender_id");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : findById
     * @author Arman sagharchi
     */
    public  Message findByReceiverId(String receiverId)
    throws IOException, ClassNotFoundException, SQLException, ConfigNotFoundException{
        return findByConfigMessage(receiverId, "receiver_id");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : insertChannel
     * @author Arman sagharchi
     */
    public  void insertMessage(String messageId, String senderId, String receiverId, MessageType type, LocalDateTime date, Object content)
    throws SQLException, IOException{

        PreparedStatement pst = connection.prepareStatement(
                "INSERT INTO message VALUES (?, ?, ?, ?, ?, ?, ?)");

        //placing the values into the query string
        pst.setString(1, messageId);
        pst.setString(2, senderId);
        pst.setString(3, receiverId);
        pst.setString(4, MessageType.getValueFromName(type));
        pst.setDate(5, new java.sql.Date(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()).getTime()));
        pst.setBytes(6, objectConvertor(new LinkedList<MessageReaction>()));
        if(type.name().equals("CHANNEL") || type.name().equals("PRIVATE_CHAT")) {
            pst.setBytes(7, (byte[]) objectConvertor(content));
        }

        //executing the query
        pst.executeUpdate();
        pst.close();


        System.out.println("data has been inserted successfully.");

    }

    /**
     * another way to insert message is by getting the whole message itself
     * @param message, message
     * @throws SQLException, while there is a problem in executing the query
     * @throws IOException, is there is a problem with converting object into byte
     */
    public void insertMessage(Message message) throws SQLException, IOException{

        //store file name in sender id
        if(message instanceof FileMessage)
        {
            message.setSenderId(message.getSenderId() + '-' + ((FileMessage) message).getFileName());
        }
        //invoking the main insert message
        insertMessage(message.getId().toString(),
                message.getSenderId(), message.getReceiverId(),message.getType(),
                message.getDate(), message.getContent());

        if(message instanceof FileMessage)
        {
            message.setSenderId(message.getSenderId().substring(0 , message.getSenderId().indexOf('-')));
        }
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : updateChannel
     * @author Arman sagharchi
     */
    public void updateMessage(String id, String type, String newValue)throws SQLException,
            ConfigNotFoundException{
        String query = "UPDATE message SET " + type +" = ? where message_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        //placing the values to the query
        st.setString(1, newValue);
        st.setString(2, id);

        //taking the response from updating
        int ans = st.executeUpdate();

        if(ans == 0){
            throw new ConfigNotFoundException(id, type, newValue);
        }
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : updateChannel
     * @author Arman sagharchi
     */
    public  boolean updateReactions(UpdateType type, String id, MessageReaction reaction)
    throws IOException, ClassNotFoundException, SQLException, ConfigNotFoundException{

        LinkedList<MessageReaction> targetList = null;

        String query = "SELECT * FROM message WHERE message_id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        if(resultSet == null){
            throw new ConfigNotFoundException(id, "message_id", "message");
        }

        Object o = null;
        while(resultSet.next()) {
            o = byteConvertor(resultSet.getBytes("reactions"));
        }
        if(o instanceof LinkedList<?>){
            targetList = (LinkedList<MessageReaction>) o;
        }

        targetList = updateByType(type, targetList, reaction);

        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE message SET " + "reactions" +" = ? WHERE message_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : deleteByChannelId
     * @author Arman sagharchi
     */
    public boolean deleteUserById(String id) throws SQLException, ConfigNotFoundException {
        return deleteById(id, "messages", "message_id", "message");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : createChannelFromData
     * @author Arman sagharchi
     */
    private Message createMessageFromData(ResultSet resultSet)
    throws SQLException, IOException, ClassNotFoundException {

        String messageId = resultSet.getString("message_id");
        String senderId = resultSet.getString("sender_id");
        String receiverId = resultSet.getString("receiver_id");
        String type = resultSet.getString("type");
        LocalDateTime date =
                convertDateToLocalDatetime(resultSet.getDate("date"));


        LinkedList<MessageReaction> reactions = null;

        Object o;

        if ((o = byteConvertor(resultSet.getBytes("reactions"))) instanceof LinkedList<?>) {
            reactions = (LinkedList<MessageReaction>) o;
        }





        Object content = byteConvertor(resultSet.getBytes("content"));

        if(content instanceof String){
                return new TextMessage(UUID.fromString(messageId), senderId, receiverId,
                        MessageType.getNameFromValue(type), date, reactions, (String)content);
        }
        else if (content instanceof byte[]){

            //extract file name from sender id
            String[] strings = senderId.split("-");
            senderId = strings[0];
            String fileName = strings[1];

            return new FileMessage(UUID.fromString(messageId), senderId , receiverId,
                    MessageType.getNameFromValue(type), date, reactions ,fileName , (byte[])content);
        }

        return null;

    }


    /**
     * this method converts sql date to local date time
     * the goal is to store the data s into the database. therefore, need a conversion
     * @param date, sql dataTime which is taken from database
     * @return the local date time, converted type of sql date
     */
    private LocalDateTime convertDateToLocalDatetime(java.sql.Date date){
        return Instant.ofEpochMilli(date.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();

    }
}
