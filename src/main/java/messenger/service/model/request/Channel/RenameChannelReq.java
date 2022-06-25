/**
 * to rename channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

public class RenameChannelReq extends ChannelReq
{
    private final String newName;

    public RenameChannelReq(String senderId, String serverId, String channelName, String newName) {
        super(senderId, ChannelRequestType.RENAME_CHANNEL, serverId, channelName);
        this.newName = newName;
    }

    public String getNewName() {
        return newName;
    }
}
