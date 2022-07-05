/**
 * this is used to creat channel
 *
 * @author mahdi
 */
package model.request.Channel;

import model.server.ChannelType;

public class CreateChannelReq extends ChannelReq
{
    private final ChannelType channelType;

    public CreateChannelReq(String senderId, String serverId,
                            String channelName, ChannelType channelType) {
        super(senderId, ChannelRequestType.CREAT_CHANNEL, serverId, channelName);
        this.channelType = channelType;
    }

    public ChannelType getChannelType() {
        return channelType;
    }
}
