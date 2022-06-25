package messenger.service.model.request.Authentication;

import messenger.api.connection.ServerThread;
import messenger.service.model.request.Request;
import messenger.service.model.request.RequestType;

public abstract class AuthenticationReq extends Request
{
    private final AuthenticationReqType subType;

    private final String id;
    private final String password;

    //server thread that this request comes from
    //if the authentication be successful this serverThread being added to connections list
    private final ServerThread serverThread;

    public AuthenticationReq(String senderId, AuthenticationReqType subType,
                             String id, String password, ServerThread serverThread) {
        super(senderId, RequestType.AUTHENTICATION);
        this.subType = subType;
        this.id = id;
        this.password = password;
        this.serverThread = serverThread;
    }

    public AuthenticationReqType getSubType() {
        return subType;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }

    public AuthenticationReqType subType()
    {
        return subType;
    }
}
