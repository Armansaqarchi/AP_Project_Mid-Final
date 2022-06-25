/**
 * to pin a message in a channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

import java.util.UUID;

public class PinMessageReq extends ChannelReq
{
    private final UUID messageId;

    public PinMessageReq(String senderId, String serverId, String channelName, UUID messageId) {
        super(senderId, ChannelRequestType.PIN_MESSAGE, serverId, channelName);
        this.messageId = messageId;
    }

    public UUID getMessageId() {
        return messageId;
    }
}
