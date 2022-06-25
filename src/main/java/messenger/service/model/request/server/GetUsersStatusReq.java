/**
 * to get users status of server
 *
 * @author mahdi
 */

package messenger.service.model.request.server;

public class GetUsersStatusReq extends ServerReq
{
    public GetUsersStatusReq(String senderId,  String serverId) {
        super(senderId, ServerRequestType.GET_USERS_STATUS, serverId);
    }
}
