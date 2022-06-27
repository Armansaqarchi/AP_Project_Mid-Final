package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.request.server.*;
import messenger.service.model.response.Response;
import messenger.service.model.response.server.GetRulesServerRes;
import messenger.service.model.response.server.GetServerInfoRes;
import messenger.service.model.response.server.GetUserStatusRes;
import messenger.service.model.server.RuleType;
import messenger.service.model.server.Server;
import messenger.service.model.user.User;
import messenger.service.model.user.UserStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class ServerService
{
    private final Database database;

    public ServerService()
    {
        database = Database.getDatabase();
    }

    public Response addRule(AddRuleServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getOwnerId().equals(request.getSenderId()))
            {
                database.getServerOp().updateServerHashList(UpdateType.ADD , server.getId(), request.getSenderId() , request.getRule());

                return new Response(request.getSenderId() , true , "rule added successfully.");
            }

            return new Response(request.getSenderId() , false , "you can't add rule to this server!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }

    }

    public Response getRules(GetRulesServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            return new GetRulesServerRes(request.getSenderId(), true , "rules got successfully." , server.getRules());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetRulesServerRes(request.getSenderId() , false , e.getMessage() , null);
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response addUser(AddUserServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getUsers().contains(request.getSenderId()))
            {
                //add user to server
                database.getServerOp().updateServerList(UpdateType.ADD , server.getId(), "users" , request.getUserIds());

                return new Response(request.getSenderId(), true , "user added to server successfully.");
            }

            return new Response(request.getSenderId(), false , "you cant add user to this server!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response creatServer(CreateServerReq request)
    {
        try
        {
            if(database.getServerOp().isExists(request.getServerId()))
            {
                return new Response(request.getSenderId(), false , "server with this id exists already.");
            }

            database.getServerOp().insertServer(request.getServerId() , request.getSenderId() , request.getName());

            if(null != request.getImage())
            {
                database.getServerOp().updateServerProfileImage(request.getImage(), request.getServerId());
            }

            return new Response(request.getSenderId(), true , "server created successfully!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (SQLException e)
        {
            throw new RuntimeException();
        }
    }

    public Response deleteServer(DeleteServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getOwnerId().equals(request.getSenderId()))
            {
                //delete server in database
                database.getServerOp().deleteServerById(request.getServerId());

                return new Response(request.getSenderId() , true , "server deleted successfully.");
            }

            return new Response(request.getSenderId() , false , "you can't delete this server!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response getUsersStatus(GetUsersStatusReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getUsers().contains(request.getSenderId()))
            {
                return new GetUserStatusRes(request.getSenderId(), true ,
                        "users status got successfully." , getUsersStatus(server.getUsers()));
            }

            return new GetUserStatusRes(request.getSenderId(), false ,
                    "you can't get users status in this server!" , null);
        }
        catch (ConfigNotFoundException e)
        {
            return new GetUserStatusRes(request.getSenderId() , false , e.getMessage() , null);
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response removeUser(RemoveUserServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getUsers().contains(request.getUserIds()))
            {
                return new Response(request.getSenderId(), false , "user is not member of this server!");
            }

            if(checkRule(request.getSenderId() , request.getServerId(), RuleType.REMOVE_MEMBER))
            {
                //remove user from server
                database.getServerOp().updateServerList(UpdateType.REMOVE , server.getId(), "users" , request.getUserIds());

                return new Response(request.getSenderId(), true , "'" + request.getUserIds() + "' removed from server successfully.");
            }

            return new Response(request.getSenderId() , false , "you can't remove user in this server!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId(), false , e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response renameServer(RenameServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(checkRule(request.getSenderId() , server.getId() , RuleType.RENAME_SERVER))
            {
                database.getServerOp().updateServerConfig(server.getId(), "name" , request.getNewName());
                return new Response(request.getSenderId() , true , "server renamed successfully.");
            }

            return new Response(request.getSenderId(), false ,
                    "you can't rename this server!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response setImage(SetServerImageReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(checkRule(request.getSenderId() , server.getId() , RuleType.SET_IMAGE))
            {
                //update profile image
                database.getServerOp().updateServerProfileImage(request.getImage(), server.getId());

                return new Response(request.getSenderId() , true , "Image set successfully.");
            }

            return new Response(request.getSenderId(), false ,
                    "you can't set image of this server!");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response getInfo(GetServerInfoReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            return new GetServerInfoRes(request.getSenderId(),true ,
                    "server info sent." , server.getImage(),
                    server.getId(), server.getOwnerId(), server.getName());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetServerInfoRes(request.getSenderId() , false , e.getMessage()
                    , null , request.getServerId(), null , null);
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    private HashMap<String, UserStatus> getUsersStatus(LinkedList<String> userIdes)
    {
        HashMap<String , UserStatus> usersStatus = new HashMap<>();

        for(String id : userIdes)
        {
            try
            {
                User user = database.getUserOp().findById(id);

                usersStatus.put(id , user.getUserStatus());
            }
            catch (ConfigNotFoundException e)
            {

            }
            catch (SQLException | IOException | ClassNotFoundException e)
            {
                throw new RuntimeException();
            }
        }

        return usersStatus;
    }

    private boolean checkRule(String userId , String serverId , RuleType ruleType)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(serverId);

            if(server.getRules().get(userId).getRules().contains(ruleType))
            {
                return true;
            }

            return false;
        }
        catch (ConfigNotFoundException e)
        {
            return false;
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }
}
