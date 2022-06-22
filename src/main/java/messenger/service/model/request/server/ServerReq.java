/**
 * this is used to get request related to server
 *
 * @author mahdi
 */

package messenger.service.model.request.server;

import messenger.service.model.request.Request;

public abstract class ServerReq extends Request
{
    private ServerRequestType type;

    private String serverId;
}
