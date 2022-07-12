package messenger.dataBaseOp;


import model.exception.ConfigNotFoundException;
import model.server.Channel;
import model.server.ChannelType;
import model.server.TextChannel;
import model.server.VoiceChannel;


import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

public class ChannelOp extends Op{


    public ChannelOp(Connection connection){
        super(connection);
    }

    /**
     * takes the configuration and column name of that config
     * then, takes the channel from database and returns the channel
     * @param config, value ot that column name
     * @param columnName, name of column which the channel is found by
     * @return the channel is the channel is found
     * @throws IOException, if channel cannot be converted to byte[]
     * @throws SQLException, if something is wrong with executing the query
     * @throws ClassNotFoundException, while casting object to byte[]
     * @throws ConfigNotFoundException, if no channel is found with this info
     * @author Arman sagharchi
     */
    public Channel findByConfigChannel(String config, String columnName)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        return createChannelFromData(findByConfig(config, columnName, "channel"));
    }

    /**
     * this method simply takes the id and passes that id to previous method
     * the goal of implementing this method is to simplify interaction between
     * database and service section
     * @param id, the channel id
     * @return the channel which is takes from previous method
     * @author Arman sagharchi
     */
    public  Channel findById(String id)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{
        //passes the id to the previous method
        return findByConfigChannel(id, "id");
    }

    /**
     * takes the name from client and passes it to findByConfig method
     * @param name, channel name
     * @return the channel which is takes from findByConfig method
     * @throws SQLException, if something is run with executing the query
     * @author Arman sagharchi
     */
    public Channel findByName(String name)
            throws SQLException, ClassNotFoundException, IOException, ConfigNotFoundException{
        return findByConfigChannel(name, "name");
    }

    /**
     * updates the configuration of a channel
     * @param id, channel id, in order to find that channel to change the configs
     * @param type, means channel name
     * @param newValue, new value to update the channel
     * @throws SQLException, while executing the query
     * @author Amrna sagharchi
     */
    public void updateChannelConfig(String id, String type, String newValue)
    throws SQLException, IOException, ClassNotFoundException{
        String query = "UPDATE channel SET " + type +" = ? where id = ?";

        //executing the query
        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        st.executeUpdate();
    }

    /**
     * updates channel list
     * this is a generic function which takes the T parameter as T is element type of list
     * and then adds the t to the linked list or removes it from the list
     * @param type, update type which could be : ADD or REMOVE
     * @param columnName, column name
     * @param id, channel id
     * @param t, new value which should be added or removed based by update type
     * @param <T>, type of new element
     * @return true if the updating was successful
     * @throws SQLException, while executing the query
     * @throws IOException while converting the object into the byte[]
     * @throws ConfigNotFoundException, if the config is not found in the database
     * @throws ClassNotFoundException, if there is any cast exception
     * @author Arman sagharchi
     */
    public <T> boolean updateChannelList(UpdateType type, String columnName, String id, T t)
            throws SQLException, IOException, ConfigNotFoundException, ClassNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM channel WHERE id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        if(resultSet == null){
            throw new ConfigNotFoundException(id, columnName, "channel");
        }

        Object o = null;
        while (resultSet.next()) {
            //converting the object into the byte
            o = byteConvertor(resultSet.getBytes(columnName));
        }
        if (o instanceof LinkedList<?>) {
            targetList = (LinkedList<T>) o;
        }

        //finds the update type
        switch (type.showValue()) {


            case "Add":
                //adds the t
                targetList = addToLists(targetList, t);
                break;

            //removes the t
            case "Remove":
                targetList = removeFromList(targetList, t);
                break;

            default:
                return false;


        }

        //again converts the object into the byte[] to save in the db
        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE channel SET " + columnName + " = ? WHERE id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);

        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);

        //executes the updates
        pst2.executeUpdate();

        return true;
    }

    /**
     * inserts the channel into the database
     * @param id, channel id
     * @param name, channel name
     * @param channelType, channel type, could be voice or text
     * @throws SQLException, while something is wrong with executing the query
     * @author Arman sagharchi
     */
    public void insertChannel(String id, String name, ChannelType channelType)
    throws SQLException, IOException, ClassNotFoundException{

        PreparedStatement pst = connection.prepareStatement(
                // the ? is a symbol of value
                //means the value will be added to the query string
                "INSERT INTO channel VALUES (?, ?, ?, ?, ?, ?)");

        //adding the values instead of ?
        pst.setString(1, id);
        pst.setString(2, name);
        pst.setString(3, ChannelType.getValueFromType(channelType));
        pst.setBytes(4, objectConvertor(new LinkedList<String>()));
        pst.setBytes(5, objectConvertor(new LinkedList<UUID>()));
        pst.setBytes(6, objectConvertor(new LinkedList<UUID>()));

        //executes the update
        pst.executeUpdate();
        pst.close();


        System.out.println("data has been inserted successfully.");
    }

    /**
     * deletes the channel by taking the channel id
     * @param id, channel id
     * @return true if channel is deleted successfully
     * @throws SQLException, same as previous methods
     * @throws ClassNotFoundException ...
     * @throws IOException ...
     * @throws ConfigNotFoundException ...
     * @author Arman sagharchi
     */
    public boolean deleteChannelById(String id) throws SQLException, ClassNotFoundException,
            IOException, ConfigNotFoundException {

        return deleteById(id, "channel", "id", "Channel");
    }

    /**
     * takes all the data from db and converts then into a channel
     * @param resultSet, takes this result which contains all the db data s
     * @return the channel which is obtained from data s
     * @throws SQLException ...
     * @throws IOException ...
     * @throws ClassNotFoundException ...
     * @author Arman sagharchi
     */
    public Channel createChannelFromData(ResultSet resultSet)
    throws SQLException, IOException, ClassNotFoundException{


        if(resultSet == null){
            return null;
        }

        //takes the channel id
        String channelId = resultSet.getString("id");
        //takes the name
        String name = resultSet.getString("name");
        LinkedList<String> users = null;
        LinkedList<UUID> messages = null;
        LinkedList<UUID> pinnedMessages = null;

        //takes the channel type
        ChannelType channelType = ChannelType.
                getTypeFromValue(resultSet.getString("channel_type"));

        //converts byte[] into object
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

        //based on type, returns the message
        if(channelType == ChannelType.TEXT){
            return new TextChannel(UUID.fromString(channelId), name, channelType,
                    users, messages, pinnedMessages);
        }
        else {
            return new VoiceChannel(UUID.fromString(channelId), name, channelType, users);
        }



    }






}
