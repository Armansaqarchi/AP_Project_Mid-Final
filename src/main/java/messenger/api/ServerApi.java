package messenger.api;


import messenger.service.ServerService;
import model.exception.InvalidTypeException;
import model.exception.ServerThreadNotFoundException;
import model.request.server.*;
import model.response.Response;

public class ServerApi
{
    private final ServerService service;

    //sender object to send responses to client
    private final Sender sender;

    public ServerApi()
    {
        service = new ServerService();
        sender = Sender.getSender();
    }
    public void getRequest(ServerReq request) throws InvalidTypeException {
        switch (request.subType())
        {
            case ADD_RULE -> addRule((AddRuleServerReq) request);
            case GET_INFO -> getInfo((GetServerInfoReq) request);
            case GET_RULES -> getRules((GetRulesServerReq) request);
            case ADD_USER -> addUser((AddUserServerReq) request);
            case SET_IMAGE -> setImage((SetServerImageReq) request);
            case CREAT_SERVER -> creatServer((CreateServerReq) request);
            case DELETE_SERVER -> deleteServer((DeleteServerReq) request);
            case RENAME_SERVER -> renameServer((RenameServerReq) request);
            case REMOVE_USER -> removeUser((RemoveUserServerReq) request);
            case GET_USERS_STATUS -> getUsersStatus((GetUsersStatusReq) request);
            default -> throw new InvalidTypeException();
        }
    }

    private void addRule(AddRuleServerReq request)
    {
        sendResponse(service.addRule(request));
    }

    private void getRules(GetRulesServerReq request)
    {
        sendResponse(service.getRules(request));
    }

    private void addUser(AddUserServerReq request)
    {
        sendResponse(service.addUser(request));
    }

    private void creatServer(CreateServerReq request)
    {
        sendResponse(service.creatServer(request));
    }

    private void deleteServer(DeleteServerReq request)
    {
        sendResponse(service.deleteServer(request));
    }

    private void getUsersStatus(GetUsersStatusReq request)
    {
        sendResponse(service.getUsersStatus(request));
    }

    private void removeUser(RemoveUserServerReq request)
    {
        sendResponse(service.removeUser(request));
    }

    private void renameServer(RenameServerReq request)
    {
        sendResponse(service.renameServer(request));
    }

    private void setImage(SetServerImageReq request)
    {
        sendResponse(service.setImage(request));
    }

    private void getInfo(GetServerInfoReq request)
    {
        sendResponse(service.getInfo(request));
    }

    private void sendResponse(Response response)
    {
        try
        {
            sender.sendResponse(response);
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
