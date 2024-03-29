package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import model.exception.ConfigNotFoundException;
import model.request.server.*;
import model.response.Response;
import model.response.server.GetRulesServerRes;
import model.response.server.GetServerInfoRes;
import model.response.server.GetUserStatusRes;
import model.server.Rule;
import model.server.RuleType;
import model.server.Server;
import model.user.ServerIDs;
import model.user.User;
import model.user.UserStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * handles requests related to service
 */
public class ServerService
{
    private final Database database;

    /**
     * the constructor of class
     */
    public ServerService()
    {
        database = Database.getDatabase();
    }

    /**
     * handles add rule request
     * @param request the add rule request
     * @return response of request
     */
    public Response addRule(AddRuleServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getOwnerId().equals(request.getSenderId()))
            {
                database.getServerOp().updateServerHashList(UpdateType.ADD ,"rules",  server.getId(), request.getRule().getId() , request.getRule());

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

    /**
     * handles get rule request
     * @param request the get rule request
     * @return response of request
     */
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

    /**
     * handles add user request
     * @param request the add user request
     * @return response of request
     */
    public Response addUser(AddUserServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getUsers().contains(request.getSenderId()))
            {
                //add user to server
                database.getServerOp().updateServerList(UpdateType.ADD , "users", server.getId(), request.getUserIds());

                ServerIDs serverId = new ServerIDs(server.getId(), new LinkedList<>());

                //add serverID to users servers
                database.getUserOp().updateList(UpdateType.ADD , "servers" , request.getUserIds() , serverId);

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

    /**
     * handles create server request
     * @param request the create server request
     * @return response of request
     */
    public synchronized Response creatServer(CreateServerReq request)
    {
        try
        {
            if(database.getServerOp().isExists(request.getServerId()))
            {
                return new Response(request.getSenderId(), false , "server with this id exists already.");
            }

            database.getServerOp().insertServer(request.getServerId() , request.getSenderId() , request.getName());

            database.getServerOp().updateServerList(UpdateType.ADD , "users" , request.getServerId(), request.getSenderId());

            ServerIDs serverId = new ServerIDs(request.getServerId(), new LinkedList<>());

            //add serverID to owners servers
            database.getUserOp().updateList(UpdateType.ADD , "servers" , request.getSenderId() , serverId);

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
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles delete server request
     * @param request the delete server request
     * @return response of request
     */
    public Response deleteServer(DeleteServerReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getOwnerId().equals(request.getSenderId()))
            {
                for(String userId : server.getUsers())
                {
                    User user = database.getUserOp().findById(userId);
                    ServerIDs serverIDs = user.getServers().get(user.getServers().indexOf(new ServerIDs(server.getId() , null)));

                    database.getUserOp().updateList(UpdateType.REMOVE , "servers" , userId , serverIDs);
                }

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

    /**
     * handles get status of server's users request
     * @param request the request
     * @return response of request
     */
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

    /**
     * handles remove user request
     * @param request the remove user request
     * @return response of request
     */
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
                database.getServerOp().updateServerList(UpdateType.REMOVE , "users" , server.getId() , request.getUserIds());

                ServerIDs serverId = new ServerIDs(server.getId(), new LinkedList<>());

                //add serverID to users servers
                database.getUserOp().updateList(UpdateType.REMOVE , "servers" , request.getUserIds() , serverId);

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

    /**
     * handles rename server request
     * @param request the rename request
     * @return response of request
     */
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

    /**
     * handles set server's image request
     * @param request the request
     * @return response of request
     */
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

    /**
     * handles get server's info request
     * @param request the request
     * @return response of request
     */
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

    /**
     * handles remove rule from server
     * @param request the request
     * @return response of request
     */
    public Response RemoveRule(RemoveRuleReq request)
    {
        try
        {
            Server server = database.getServerOp().findByServerId(request.getServerId());

            if(server.getOwnerId().equals(request.getSenderId()))
            {
                //get rule of this user in server
                Rule rule = server.getRules().get(request.getUserId());

                if(null == rule)
                {
                    return new Response(request.getSenderId() , false ,
                            "user : " + request.getUserId() + " has no rule in server!");
                }

                //remove rule
                database.getServerOp().updateServerHashList(UpdateType.REMOVE , "rules", server.getId(), request.getUserId() , rule);

                //remove rule types that are sent in request
                for(RuleType ruleType : request.getRules())
                {
                    rule.getRules().remove(ruleType);
                }

                //add new rule if it is not empty
                if(! rule.getRules().isEmpty())
                {
                    database.getServerOp().updateServerHashList(UpdateType.ADD ,"rules", server.getId(), request.getUserId() , rule);
                }

                return new Response(request.getSenderId() , true , "rules removed successfully.");
            }

            return new Response(request.getSenderId() , false , "you can't remove rule of this server!");
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

            if(server.getOwnerId().equals(userId))
            {
                return true;
            }

            if(server.getRules().get(userId).getRules().contains(ruleType))
            {
                return true;
            }

            return false;
        }
        catch (ConfigNotFoundException | NullPointerException e)
        {
            return false;
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }
}
