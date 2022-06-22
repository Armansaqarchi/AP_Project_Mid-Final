/**
 * this is used to add a list of users to channel
 *
 * @author mahdi
 */

package messenger.service.model.request.Channel;

import java.util.LinkedList;

public class AddUsersChannelReq extends ChannelReq
{
    private LinkedList<String> userIds;
}
