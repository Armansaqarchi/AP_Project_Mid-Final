/**
 * this is used to get request related to channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

import messenger.service.model.request.server.ServerReq;

public abstract class ChannelReq extends ServerReq
{
    private ChannelRequestType type;

    private String channelName;
}
