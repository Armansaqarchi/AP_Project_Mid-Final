/**
 * this is used to delete channel
 *
 * @author mahdi
 */

package model.request.Channel;

public class DeleteChannelReq extends ChannelReq
{
    public DeleteChannelReq(String senderId,  String serverId, String channelName) {
        super(senderId, ChannelRequestType.DELETE_CHANNEL, serverId, channelName);
    }
}
