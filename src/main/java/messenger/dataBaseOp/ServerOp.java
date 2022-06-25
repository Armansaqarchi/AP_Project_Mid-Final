package messenger.dataBaseOp;

import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.message.MessageType;
import messenger.service.model.message.Reaction;
import messenger.service.model.server.*;

import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;


class ServerOp extends Op{



    public ServerOp(Connection connection){
        super(connection);
    }

    private Server findByConfigMessage(String config, String columnName)
            throws IOException, SQLException, ClassNotFoundException{
        return createServerFromData(findByConfig(config, columnName, "server"));
    }

    public  Server findByServerId(String id)
            throws IOException, SQLException, ClassNotFoundException{
        return findByConfigMessage(id, "server_id");
    }

    public  Server findByOwnerId(String ownerId)
            throws IOException, ClassNotFoundException, SQLException{
        return findByConfigMessage(ownerId, "owner_id");
    }

    public  Server findByReceiverId(String name)
            throws IOException, ClassNotFoundException, SQLException{
        return findByConfigMessage(name, "name");
    }

    public void insertServer(String id, String ownerId, String name) throws SQLException{
        PreparedStatement pst = connection.prepareStatement(
                "INSERT INTO server VALUES (?, ?, ?)");

        pst.setString(1, id);
        pst.setString(2, ownerId);
        pst.setString(3, name);

        pst.executeUpdate();
        pst.close();


        System.out.println("data has been inserted successfully.");
    }


    public void updateServerConfig(String id, String type, String newValue) throws SQLException{
        String query = "UPDATE server SET " + type +" = ? where server_id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        st.executeUpdate();
    }


    public <T> boolean updateServerList(UpdateType type, String columnName, String id, T t)
        throws SQLException, IOException, ClassNotFoundException {

        LinkedList<T> targetList = null;

        String query = "SELECT * FROM server WHERE server_id = ?";

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

        String query2 = "UPDATE server SET " + columnName + " = ? WHERE server_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);
        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }


    public boolean deleteServerById(String id) throws SQLException, ConfigNotFoundException {
        return deleteById(id, "server", "server_id", "server");
    }


    public <T,U>boolean updateServerHashList(UpdateType type, String id, T key, U value)
    throws SQLException, IOException, ClassNotFoundException{

        HashMap<T, U> targetList = null;

        String query = "SELECT * FROM server WHERE server_id = ?";

        PreparedStatement pst = connection.prepareStatement(query);

        pst.setString(1, id);
        ResultSet resultSet = pst.executeQuery();

        Object o = null;
        while (resultSet.next()) {
            o = byteConvertor(resultSet.getBytes("rules"));
        }
        if (o instanceof HashMap<?,?>) {
            targetList = (HashMap<T, U>) o;
        }


        switch (type.showValue()) {


            case "Add":
                if(targetList == null){
                    targetList = new HashMap<>();
                }
                targetList.put(key, value);
                break;

            case "Remove":
                targetList.remove(key, value);
                break;

            default:
                return false;

        }


        byte[] updatedList = objectConvertor(targetList);

        String query2 = "UPDATE server SET rules = ? WHERE server_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query2);
        pst2.setBytes(1, updatedList);
        pst2.setString(2, id);


        pst2.executeUpdate();

        return true;
    }


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




}
