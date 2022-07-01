package messenger.api;

import messenger.api.connection.ConnectionHandler;
import messenger.service.AuthenticationService;
import model.exception.InvalidTypeException;
import model.exception.ServerThreadNotFoundException;
import model.request.Authentication.AuthenticationReq;
import model.request.Authentication.LoginReq;
import model.request.Authentication.SignupReq;
import model.response.Response;

/**
 * it is used to get and handle requests related to authentication
 */
public class AuthenticationApi
{

    private final AuthenticationService service;

    //sender object to send responses to client
    private final Sender sender;

    /**
     * the constructor of class that initializes fileds
     */
    public AuthenticationApi()
    {
        service = new AuthenticationService();
        sender = Sender.getSender();
    }

    /**
     * gets and handles authentication requests
     * @param request the authentication request
     * @throws InvalidTypeException throws if inputted requests type was invalid
     */
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
        handleResponse(service.login(request) , request);
    }

    private void signup(SignupReq request)
    {
        handleResponse(service.signup(request) , request);
    }

    private void handleResponse(Response response , AuthenticationReq request)
    {
        try {
            sender.sendResponse(response , request.getServerThread());
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        if(response.isAccepted())
        {
            //add new client to connections list
            ConnectionHandler.getConnectionHandler().
                    addConnection(request.getId() , request.getServerThread());

            //set serverThreads id
            request.getServerThread().verified(request.getId());
        }
    }
}
