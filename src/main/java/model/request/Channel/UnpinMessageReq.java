package model.request.Channel;

import java.util.UUID;

public class UnpinMessageReq extends ChannelReq
{
    private final UUID messageId;

    public UnpinMessageReq(String senderId, String serverId, String channelName, UUID messageId)
    {
        super(senderId, ChannelRequestType.UN_PIN_MESSAGE , serverId, channelName);
        this.messageId = messageId;
    }

    public UUID getMessageId() {
        return messageId;
    }
}
