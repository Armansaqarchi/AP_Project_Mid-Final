/**
 * this is used to get request related to server
 *
 * @author mahdi
 */

package model.request.server;

import model.request.Request;
import model.request.RequestType;

public abstract class ServerReq extends Request
{
    private final ServerRequestType subType;

    private final String serverId;

    public ServerReq(String senderId, ServerRequestType subType, String serverId) {
        super(senderId, RequestType.SERVER);
        this.subType = subType;
        this.serverId = serverId;
    }

    public ServerRequestType subType() {
        return subType;
    }

    public String getServerId() {
        return serverId;
    }
}
