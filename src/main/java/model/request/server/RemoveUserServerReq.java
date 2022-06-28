/**
 * remove users from server
 *
 * @author mahdi
 */

package model.request.server;

public class RemoveUserServerReq extends ServerReq
{
    private final String userId;

    public RemoveUserServerReq(String senderId,  String serverId, String userId) {
        super(senderId, ServerRequestType.REMOVE_USER, serverId);
        this.userId = userId;
    }

    public String getUserIds() {
        return userId;
    }
}
