package messenger.dataBaseOp;

import model.exception.ConfigNotFoundException;
import model.request.user.FriendReq;
import model.request.user.UserRequestType;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class FriendRequestOp extends Op{


    public FriendRequestOp(Connection connection){
        super(connection);
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class
     * @author Arman sagharchi
     */
    public FriendReq findByConfigFriendReq(String config, String columnName)
            throws SQLException, ConfigNotFoundException{
        return createFriendReqFromData(findByConfig(config, columnName, "friend_requests"));
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class
     * @author Arman sagharchi
     */
    public  FriendReq findById(String id)
            throws IOException, SQLException, ClassNotFoundException,
            ConfigNotFoundException, ConfigNotFoundException{
        return findByConfigFriendReq(id, "id");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class
     * @author Arman sagharchi
     */
    public  FriendReq findBySenderId(String senderId)
            throws IOException, ClassNotFoundException,
            SQLException, ConfigNotFoundException{
        return findByConfigFriendReq(senderId, "sender_id");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class
     * @author Arman sagharchi
     */
    public  FriendReq findByReceiverId(String receiverId)
            throws IOException, ClassNotFoundException,
            SQLException, ConfigNotFoundException{
        return findByConfigFriendReq(receiverId, "receiver_id");
    }


    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class
     * @author Arman sagharchi
     */
    public void insertFriendReq(UUID id, String receiver, UserRequestType type, String sender_id)
    throws SQLException{

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO friend_requests VALUES (?, ?, ?, ?)");

        ps.setString(1, id.toString());
        ps.setString(2, receiver);
        ps.setString(3, UserRequestType.getValueFromName(type));
        ps.setString(4, sender_id);



        ps.executeUpdate();
        ps.close();

        System.out.println("data has been inserted successfully.");
    }

    /**
     * the same as channelOp
     * there is a parallel method is channelOp
     * @see ChannelOp class
     * @author Arman sagharchi
     */
    public void insertFriendReq(FriendReq friendReq) throws IOException, SQLException{
        insertFriendReq(friendReq.getId(), friendReq.getReceiver(),
                friendReq.subType(), friendReq.getSenderId());
    }

    /**
     * updates the friendRequest fields in the db
     * @param id, friend req id
     * @param type, update type
     * @param newValue, new value
     * @see ChannelOp, method : updateChannel
     * @author Arman sagharchi
     */
    public void updateProfile(String id, String type, String newValue)throws SQLException,
            ConfigNotFoundException{
        String query = "UPDATE friend_requests SET " + type +" = ? where id = ?";

        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, newValue);
        st.setString(2, id);


        int res = st.executeUpdate();

        if(res == 0){
            throw new ConfigNotFoundException(id, type, "friend request");
        }
    }

    /**
     * there is a parallel method in channelOp
     * @see ChannelOp, method : delete channel by id
     * @author Arman sagharchi
     */
    public boolean deleteFriendRequestById(String id) throws ConfigNotFoundException, SQLException {
        return deleteById(id, "friend_requests", "id", "friendRequest");

    }

    /**
     * @see ChannelOp, method : createChannelFromData
     * @author Arman sagharchi
     */
    public FriendReq createFriendReqFromData(ResultSet resultSet) throws SQLException{


        if(resultSet == null){
            return null;
        }

        //getting info s from db
        String friendReqId = resultSet.getString("id");
        String senderId = resultSet.getString("sender_id");
        String receiverId = resultSet.getString("receiver_id");


        return new FriendReq(senderId, UUID.fromString(friendReqId), receiverId);

    }


}
