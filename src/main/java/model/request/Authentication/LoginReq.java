package model.request.Authentication;

import messenger.api.connection.ServerThread;

public class LoginReq extends AuthenticationReq
{
    public LoginReq(String senderId, String id, String password, ServerThread serverThread)
    {
        super(senderId, AuthenticationReqType.LOGIN, id, password, serverThread);
    }
}
