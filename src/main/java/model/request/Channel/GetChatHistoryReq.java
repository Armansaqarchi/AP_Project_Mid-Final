/**
 * to get chat history of a channel
 *
 * @author mahdi
 */
package model.request.Channel;

public class GetChatHistoryReq extends ChannelReq
{
    public GetChatHistoryReq(String senderId, String serverId, String channelName) {
        super(senderId, ChannelRequestType.GET_CHAT_HISTORY, serverId, channelName);
    }
}
