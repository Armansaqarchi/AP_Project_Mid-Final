/**
 * to delete server
 *
 * @author mahdi
 */
package messenger.service.model.request.server;

public class DeleteServerReq extends ServerReq
{
    public DeleteServerReq(String senderId, String serverId) {
        super(senderId, ServerRequestType.DELETE_SERVER, serverId);
    }
}
