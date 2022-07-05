package messenger.dataBaseOp;

import model.exception.ConfigNotFoundException;
import model.server.Rule;
import model.server.Server;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;


public class ServerOp extends Op{



    public ServerOp(Connection connection){
        super(connection);
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : findByConfigChannel
     * @author Arman sagharchi
     */
    private Server findByConfigMessage(String config, String columnName)
            throws IOException, SQLException,
            ClassNotFoundException, ConfigNotFoundException{
        return createServerFromData(findByConfig(config, columnName, "server"));
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : findById
     * @author Arman sagharchi
     */
    public  Server findByServerId(String id)
            throws IOException, SQLException,
            ClassNotFoundException, ConfigNotFoundException{
        return findByConfigMessage(id, "server_id");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : updateProfile
     * @author Arman sagharchi
     */
    public void updateServerProfileImage(byte[] newImage ,String id)
            throws ConfigNotFoundException, SQLException{

        updateImage(newImage, id, "image", "server",
                "server_id", "server");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : insertChannel
     * @author Arman sagharchi
     */
    public void insertServer(String id, String ownerId, String name) throws SQLException,
            IOException{
        PreparedStatement pst = connection.prepareStatement(
                "INSERT INTO server VALUES (?, ?, ?, ?, ?, ?)");

        pst.setString(1, id);
        pst.setString(2, ownerId);
        pst.setString(3, name);
        pst.setBytes(4, objectConvertor(new LinkedList<String>()));
        pst.setBytes(5, objectConvertor(new HashMap<String, Rule>()));
        pst.setBytes(6, objectConvertor(new HashMap<String, UUID>()));

        pst.executeUpdate();
        pst.close();


        System.out.println("data has been inserted successfully.");
    }


    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : updateProfile
     * @author Arman sagharchi
     */
    public void updateServerConfig(String id, String type, String newValue)
            throws SQLException, ConfigNotFoundException{
        String query = "UPDATE server SET " + type +" = ? where server_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        if(st.executeUpdate() == 0){
            throw new ConfigNotFoundException(id, type, "server");
        }
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : updateChannelList
     * @author Arman sagharchi
     */
    public <T> boolean updateServerList(UpdateType type, String columnName, String id, T t)
        throws SQLException, IOException, ClassNotFoundException, ConfigNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM server WHERE server_id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        if(resultSet == null){
            throw new ConfigNotFoundException(id, columnName, "server");
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

        String query2 = "UPDATE server SET " + columnName + " = ? WHERE server_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);
        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }


    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : deleteChannelById
     * @author Arman sagharchi
     */
    public boolean deleteServerById(String id) throws SQLException, ConfigNotFoundException {
        return deleteById(id, "server", "server_id", "server");
    }

    /**
     * this method updates any hash list existing in server details
     * the method is generic, so it takes one parameter t which is of type hash list's elements
     * and also another parameter u, t rules as a key and u rules as a value.
     * @param type, type of update, could be : add or remove
     * @param columnName, name of column
     * @param id, server id
     * @param key, elements key
     * @param value, elements value
     * @param <T>, type of element's key being added to the hash list
     * @param <U>, type of element's value being added to the hash list
     * @return true if the modification was successful, false if its was now
     * @throws SQLException, is thrown while there is something wrong with executing the query
     * @throws IOException, while there is something wrong with conversion of byte convertor
     * @author Arman sagharchi
     */
    public <T,U>boolean updateServerHashList(UpdateType type, String columnName, String id, T key, U value)
    throws SQLException, IOException, ClassNotFoundException, ConfigNotFoundException{

        HashMap<T, U> targetList = null;

        //the query
        String query = "SELECT * FROM server WHERE server_id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        //placing value instead of ? in query
        pst.setString(1, id);
        //executing query
        //a result is returned by this method including servers which matches the given info s
        ResultSet resultSet = pst.executeQuery();

        if(resultSet == null){
            //if result set is null, it means nothing was found with this info s
            throw new ConfigNotFoundException(id, "server_id", "server");
        }

        Object o = null;
        while (resultSet.next()) {
            //converting hash list bytes to object in order to modify
            o = byteConvertor(resultSet.getBytes("rules"));
        }
        if (o instanceof HashMap<?,?>) {
            targetList = (HashMap<T, U>) o;
        }


        switch (type.showValue()) {

            //operation is executed based on type of update
            case "Add":
                if(targetList == null){
                    targetList = new HashMap<>();
                }
                targetList.put(key, value);
                break;

            case "Remove":
                if(targetList == null){
                    targetList = new HashMap<>();
                }
                targetList.remove(key, value);
                break;

            default:
                return false;

        }

        //converting the object to the byte again in order to store in the db
        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE server SET " + columnName +  " = ? WHERE server_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);
        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : createChannelFromData
     * @author Arman sagharchi
     */
    public Server createServerFromData(ResultSet resultSet)
            throws SQLException, IOException, ClassNotFoundException{

        if(resultSet == null){
            return null;
        }

        String serverId = resultSet.getString("server_id");
        String name = resultSet.getString("name");
        String ownerId = resultSet.getString("owner_id");
        byte[] image = resultSet.getBytes("image");
        LinkedList<String> users = null;
        HashMap<String, Rule> rules = null;
        HashMap<String, UUID> channels = null;


        Object o = byteConvertor(resultSet.getBytes("users"));
        if(o instanceof LinkedList<?>){
            users = (LinkedList<String>) o;
        }

        o = byteConvertor(resultSet.getBytes("rules"));
        if(o instanceof HashMap<?,?>){
            rules = (HashMap<String, Rule>) o;
        }

        o = byteConvertor(resultSet.getBytes("channels"));
        if(o instanceof HashMap<?,?>){
            channels = (HashMap<String, UUID>) o;
        }

        return new Server(image, serverId, ownerId, name, users, rules, channels);

    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class, method : isExists
     * @author Arman sagharchi
     */
    public boolean isExists(String id)
    {
        try
        {
            Server server = findByServerId(id);
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
