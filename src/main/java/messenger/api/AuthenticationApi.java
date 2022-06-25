package messenger.api;

import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.request.Authentication.AuthenticationReq;
import messenger.service.model.request.Authentication.LoginReq;
import messenger.service.model.request.Authentication.SignupReq;

public class AuthenticationApi
{

    public void getRequest(AuthenticationReq request) throws InvalidTypeException {
        switch(request.subType())
        {
            case LOGIN -> login((LoginReq) request);
            case SIGNUP -> signup((SignupReq) request);
            default -> throw new InvalidTypeException();
        }
    }

    private void login(LoginReq request)
    {

    }

    private void signup(SignupReq request)
    {

    }
}
