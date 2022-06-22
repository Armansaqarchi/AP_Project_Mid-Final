/**
 * remove users from channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

import java.util.LinkedList;

public class RemoveUsersChannelReq extends ChannelReq
{
    private LinkedList<String> userIds;
}
