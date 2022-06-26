package messenger.api;

import messenger.api.connection.ConnectionHandler;
import messenger.service.AuthenticationService;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.exception.ServerThreadNotFoundException;
import messenger.service.model.request.Authentication.AuthenticationReq;
import messenger.service.model.request.Authentication.LoginReq;
import messenger.service.model.request.Authentication.SignupReq;
import messenger.service.model.response.Response;

public class AuthenticationApi
{

    AuthenticationService service;
    Sender sender;

    public AuthenticationApi()
    {
        service = new AuthenticationService();
        sender = Sender.getSender();
    }

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
        Response response = service.login(request);

        if(response.isAccepted())
        {
            //add new client to connections list
            ConnectionHandler.getConnectionHandler().
                    addConnection(request.getId() , request.getServerThread());
        }

        try {
            sender.sendResponse(response , request.getServerThread());
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void signup(SignupReq request)
    {

        Response response = service.signup(request);

        try {
            sender.sendResponse(response , request.getServerThread());
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
