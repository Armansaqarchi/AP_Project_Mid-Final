package messenger.dataBaseOp;


import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.server.Channel;
import messenger.service.model.server.ChannelType;
import messenger.service.model.server.TextChannel;
import messenger.service.model.server.VoiceChannel;


import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

public class ChannelOp extends Op{


    public ChannelOp(Connection connection){
        super(connection);
    }

    public Channel findByConfigChannel(String config, String columnName)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return createChannelFromData(findByConfig(config, columnName, "channel"));
    }

    public  Channel findById(String id)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return findByConfigChannel(id, "channel_id");
    }

    public Channel findByName(String name)
            throws SQLException, ClassNotFoundException, IOException, ConfigNotFoundException{
        return findByConfigChannel(name, "name");
    }

    public void updateChannelConfig(String id, String type, String newValue)
    throws SQLException, IOException, ClassNotFoundException{
        String query = "UPDATE channel SET " + type +" = ? where channel_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        st.executeUpdate();
    }

    public <T> boolean updateChannelList(UpdateType type, String columnName, String id, T t)
            throws SQLException, IOException, ConfigNotFoundException, ClassNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM channel WHERE channel_id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        if(resultSet == null){
            throw new ConfigNotFoundException(id, columnName, "channel");
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

        String query2 = "UPDATE channel SET " + columnName + " = ? WHERE channel_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, "3");


        pst2.executeUpdate();

        return true;
    }



    public void insertChannel(String id, String name, ChannelType channelType)
    throws SQLException, IOException, ClassNotFoundException{

        PreparedStatement pst = connection.prepareStatement(
                "INSERT INTO channel VALUES (?, ?, ?, ?, ?, ?)");

        pst.setString(1, id);
        pst.setString(2, name);
        pst.setString(3, ChannelType.getValueFromType(channelType));
        pst.setNull(4, Types.BINARY);
        pst.setNull(5,Types.BINARY);
        pst.setNull(6, Types.BINARY);

        pst.executeUpdate();
        pst.close();


        System.out.println("data has been inserted successfully.");
    }

    public boolean deleteChannelById(String id) throws SQLException, ClassNotFoundException,
            IOException, ConfigNotFoundException {

        return deleteById(id, "channel", "channel_id", "Channel");
    }


    public Channel createChannelFromData(ResultSet resultSet)
    throws SQLException, IOException, ClassNotFoundException{


        if(resultSet == null){
            return null;
        }

        String channelId = resultSet.getString("channel_id");
        String name = resultSet.getString("name");
        LinkedList<String> users = null;
        LinkedList<UUID> messages = null;
        LinkedList<UUID> pinnedMessages = null;

        ChannelType channelType = ChannelType.
                getTypeFromValue(resultSet.getString("channel_type"));

        Object o = byteConvertor(resultSet.getBytes("users"));
        if(o instanceof LinkedList<?>){
            users = (LinkedList<String>) o;
        }

        o = byteConvertor(resultSet.getBytes("messages"));
        if(o instanceof LinkedList<?>){
            messages = (LinkedList<UUID>) o;
        }

        o = byteConvertor(resultSet.getBytes("pinned_messages"));
        if(o instanceof LinkedList<?>){
            pinnedMessages = (LinkedList<UUID>) o;
        }

        if(channelType == ChannelType.TEXT){
            return new TextChannel(UUID.fromString(channelId), name, channelType,
                    users, messages, pinnedMessages);
        }
        else {
            return new VoiceChannel(UUID.fromString(channelId), name, channelType, users);
        }



    }






}
