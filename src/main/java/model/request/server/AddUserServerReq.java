/**
 * to add users to server
 *
 * @author mahdi
 */

package model.request.server;

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
