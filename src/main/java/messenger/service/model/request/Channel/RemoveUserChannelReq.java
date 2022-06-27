/**
 * remove users from channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

public class RemoveUserChannelReq extends ChannelReq
{
    private final String userId;

    public RemoveUserChannelReq(String senderId, String serverId, String channelName, String userId) {
        super(senderId, ChannelRequestType.REMOVE_USER, serverId, channelName);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
