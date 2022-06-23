package messenger.service.model.request.Authentication;

import messenger.service.model.request.Request;

public abstract class AuthenticationReq extends Request
{
    private AuthenticationReqType type;

    private String id;
    private String password;

    public AuthenticationReqType subType()
    {
        return type;
    }
}
