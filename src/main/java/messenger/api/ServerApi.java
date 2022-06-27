package messenger.api;


import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.request.server.*;

public class ServerApi
{
    public void getRequest(ServerReq request) throws InvalidTypeException {
        switch (request.subType())
        {
            case ADD_RULE -> addRule((AddRuleServerReq) request);
            case GET_INFO -> getInfo((GetServerInfoReq) request);
            case GET_RULES -> getRules((GetRulesServerReq) request);
            case ADD_USER -> addUser((AddUserServerReq) request);
            case SET_IMAGE -> setImage((SetServerImageReq) request);
            case CREAT_SERVER -> creatServer((CreatServerReq) request);
            case DELETE_SERVER -> deleteServer((DeleteServerReq) request);
            case RENAME_SERVER -> renameServer((RenameServerReq) request);
            case REMOVE_USER -> removeUser((RemoveUserServerReq) request);
            case GET_USERS_STATUS -> getUsersStatus((GetUsersStatusReq) request);
            default -> throw new InvalidTypeException();
        }
    }

    private void addRule(AddRuleServerReq request)
    {

    }

    private void getRules(GetRulesServerReq request)
    {

    }

    private void addUser(AddUserServerReq request)
    {

    }

    private void creatServer(CreatServerReq request)
    {

    }

    private void deleteServer(DeleteServerReq request)
    {

    }

    private void getUsersStatus(GetUsersStatusReq request)
    {

    }

    private void removeUser(RemoveUserServerReq request)
    {

    }

    private void renameServer(RenameServerReq request)
    {

    }

    private void setImage(SetServerImageReq request)
    {

    }

    private void getInfo(GetServerInfoReq request)
    {

    }
}
