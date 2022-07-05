/**
 * this is used to get request related to channel
 *
 * @author mahdi
 */

package model.request.Channel;

import model.request.Request;
import model.request.RequestType;

public abstract class ChannelReq extends Request
{
    private final ChannelRequestType subType;

    private final String serverId;
    private final String channelName;

    public ChannelReq(String senderId, ChannelRequestType subType, String serverId, String channelName) {
        super(senderId, RequestType.CHANNEL);
        this.subType = subType;
        this.serverId = serverId;
        this.channelName = channelName;
    }

    public String getServerId() {
        return serverId;
    }

    public String getChannelName() {
        return channelName;
    }

    public ChannelRequestType subType()
    {
        return subType;
    }
}
