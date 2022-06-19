/**
 * to pin a message in a channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

import java.util.UUID;

public class PinMessageReq extends ChannelReq
{
    private UUID messageId;
}
