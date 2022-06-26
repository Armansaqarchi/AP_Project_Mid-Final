package messenger.service.model.request.Authentication;

import messenger.api.connection.ServerThread;
import messenger.service.model.request.Authentication.AuthenticationReq;

public class LoginReq extends AuthenticationReq
{

    public LoginReq(AuthenticationReqType type, String id, String password, ServerThread serverThread) {
        super(type, id, password, serverThread);
    }
}
