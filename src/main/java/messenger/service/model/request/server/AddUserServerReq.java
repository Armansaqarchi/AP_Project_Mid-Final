/**
 * to add users to server
 *
 * @author mahdi
 */

package messenger.service.model.request.server;

import java.util.LinkedList;

public class AddUserServerReq extends ServerReq
{
    private final String userIds;

    public AddUserServerReq(String senderId,  String serverId, String userIds) {
        super(senderId, ServerRequestType.ADD_USER, serverId);
        this.userIds = userIds;
    }

    public String getUserIds() {
        return userIds;
    }
}
