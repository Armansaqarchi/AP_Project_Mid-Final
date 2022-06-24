package messenger.api;

import messenger.service.model.request.Authentication.AuthenticationReq;
import messenger.service.model.request.Authentication.LoginReq;
import messenger.service.model.request.Authentication.SignupReq;

public class AuthenticationApi
{

    public void getRequest(AuthenticationReq request)
    {
        switch(request.subType())
        {
            case LOGIN -> login((LoginReq) request);
            case SIGNUP -> signup((SignupReq) request);
            //default -> trow invalid type exception
        }
    }

    private void login(LoginReq request)
    {

    }

    private void signup(SignupReq request)
    {

    }
}
