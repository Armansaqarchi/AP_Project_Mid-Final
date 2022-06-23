/**
 * this is used to get request related to channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

import messenger.service.model.request.Request;

public abstract class ChannelReq extends Request
{
    private ChannelRequestType type;

    private String serverId;
    private String channelName;

    public ChannelRequestType subType()
    {
        return type;
    }
}
