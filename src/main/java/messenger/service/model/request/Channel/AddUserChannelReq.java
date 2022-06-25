/**
 * this is used to add a list of users to channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

public class AddUserChannelReq extends ChannelReq
{
    private final String userId;

    public AddUserChannelReq(String senderId, String serverId
            , String channelName, String userId)
    {
        super(senderId, ChannelRequestType.ADD_USER, serverId, channelName);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
