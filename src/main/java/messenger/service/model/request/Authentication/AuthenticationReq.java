package messenger.service.model.request.Authentication;

import messenger.service.model.request.Request;

public abstract class AuthenticationReq extends Request
{
    private String id;
    private String password;
}
