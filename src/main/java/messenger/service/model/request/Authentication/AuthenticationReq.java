package messenger.service.model.request.Authentication;

import messenger.api.connection.ServerThread;
import messenger.service.model.request.Request;

public abstract class AuthenticationReq extends Request
{
    private AuthenticationReqType type;

    private String id;
    private String password;

    //server thread that this request comes from
    //if the authentication be successful this serverThread being added to connections list
    private ServerThread serverThread;

    public AuthenticationReqType subType()
    {
        return type;
    }
}
