package model.request.Channel;

public class GetPinnedMsgReq extends ChannelReq
{
    public GetPinnedMsgReq(String senderId, String serverId, String channelName) {
        super(senderId, ChannelRequestType.GET_PINNED_MESSAGES, serverId, channelName);
    }
}
