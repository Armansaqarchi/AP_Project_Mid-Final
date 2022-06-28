package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.exception.UserNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.request.Channel.*;
import messenger.service.model.request.Request;
import messenger.service.model.response.Response;
import messenger.service.model.response.channel.GetChatHistoryRes;
import messenger.service.model.response.channel.GetPinnedMsgRes;
import messenger.service.model.server.*;
import messenger.service.model.user.ServerIDs;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class ChannelService
{
    private final Database database;

    public ChannelService()
    {
        database = Database.getDatabase();
    }

    public Response addUser(AddUserChannelReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            UUID channelId = server.getChannels().get(request.getChannelName());

            if(!server.getUsers().contains(request.getUserId()))
            {
                return new Response(request.getSenderId() , false ,
                        "user is not member of this server.");
            }

            if(null == channelId)
            {
                return new Response(request.getSenderId() , false ,
                        "channel : '" + request.getChannelName() + "' not found in server : '"
                                + request.getServerId() + "'");
            }

            if(!database.getChannelOp().findById(channelId.toString()).getUsers().contains(request.getUserId()))
            {
                return new Response(request.getSenderId() , false ,
                        "you cant add user to this channel!");
            }

            //add user to channel
            database.getChannelOp().updateChannelList(UpdateType.ADD , "users" ,
                    channelId.toString() , request.getUserId());

            //add channel to users list
            updateChannelsUser(request);

            return new Response(request.getSenderId() , true , "user added to channel successfully.");
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

    public Response creatChannel(CreateChannelReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!checkRule(request.getSenderId() , server.getId(), RuleType.CREATE_CHANNEL))
            {
                return new Response(request.getSenderId(), false ,
                        "you can't creat channel in this server!");
            }

            if(server.getChannels().containsKey(request.getChannelName()))
            {
                return new Response(request.getSenderId() , false ,
                        "channel with this name is exists already!");
            }

            //incomplete
            //voice channel section
            if(ChannelType.VOICE == request.getChannelType())
            {
                return new Response(request.getSenderId() , false , "voice channel is not allowed yet!");
            }
            else
            {
                TextChannel channel = new TextChannel(UUID.randomUUID() , request.getChannelName() , ChannelType.TEXT);

                //adding users of server into channel
                for(String userId : server.getUsers())
                {
                    database.getChannelOp().updateChannelList(UpdateType.ADD , "users" , channel.getId().toString() , userId);
                }

                return new Response(request.getSenderId(), true , "channel added successfully.");

            }
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

    public Response deleteChannel(DeleteChannelReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getChannels().containsKey(request.getChannelName()))
            {
                return new Response(request.getSenderId() , false ,
                        "channel not found in server!");
            }

            if(!checkRule(request.getSenderId() , server.getId(), RuleType.DELETE_CHANNEL))
            {
                return new Response(request.getSenderId(), false ,
                        "you can't delete channel in this server!");
            }

            UUID channelId = server.getChannels().get(request.getChannelName());

            //delete channel
            database.getChannelOp().deleteChannelById(channelId.toString());

            return new Response(request.getSenderId() , true ,
                    "channel deleted successfully.");
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

    public Response getChatHistory(GetChatHistoryReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getChannels().containsKey(request.getChannelName()))
            {
                return new GetChatHistoryRes(request.getSenderId() , false ,
                        "channel not found in server!" , null);
            }

            UUID channelId = server.getChannels().get(request.getChannelName());

            TextChannel channel = (TextChannel) database.getChannelOp().findById(channelId.toString());

            if(channel.getUsers().contains(request.getSenderId()))
            {
                return new GetChatHistoryRes(request.getSenderId(), true ,
                        "chat history sent" , getMessages(channel.getMessages()));
            }
            else
            {
                return new GetChatHistoryRes(request.getSenderId(), false ,
                        "you don't have access to this channel." , null);
            }
        }
        catch (ConfigNotFoundException e)
        {
            return new GetChatHistoryRes(request.getSenderId() , false , e.getMessage() , null);
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response pinMessage(PinMessageReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getChannels().containsKey(request.getChannelName()))
            {
                return new GetChatHistoryRes(request.getSenderId() , false ,
                        "channel not found in server!" , null);
            }

            if(!checkRule(request.getSenderId(), server.getId() , RuleType.PIN_MESSAGE))
            {
                return new Response(request.getSenderId() ,false ,"you can't pin message in this server");
            }

            UUID channelId = server.getChannels().get(request.getChannelName());

            database.getChannelOp().updateChannelList(UpdateType.ADD , "pinned_messages" ,
                    channelId.toString() , request.getMessageId());

            return new Response(request.getSenderId() , true ,
                    "message pinned successfully.");
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

    public Response getPinnedMessage(GetPinnedMsgReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getChannels().containsKey(request.getChannelName()))
            {
                return new GetPinnedMsgRes(request.getSenderId() , false ,
                        "channel not found in server!" , null);
            }

            UUID channelId = server.getChannels().get(request.getChannelName());

            TextChannel channel = (TextChannel) database.getChannelOp().findById(channelId.toString());

            if(channel.getUsers().contains(request.getSenderId()))
            {
                return new GetPinnedMsgRes(request.getSenderId(), true ,
                        "pinned messages sent" , getMessages(channel.getPinnedMessages()));
            }
            else
            {
                return new GetPinnedMsgRes(request.getSenderId(), false ,
                        "you don't have access to this channel." , null);
            }
        }
        catch (ConfigNotFoundException e)
        {
            return new GetPinnedMsgRes(request.getSenderId() , false , e.getMessage() , null);
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    public Response removeUser(RemoveUserChannelReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getUsers().contains(request.getUserId()))
            {
                return new Response(request.getSenderId() , false ,
                        "user is not member of this server.");
            }

            if(!server.getChannels().containsKey(request.getChannelName()))
            {
                return new Response(request.getSenderId() , false ,
                        "channel not found in server!");
            }

            if(!checkRule(request.getSenderId(), server.getId() , RuleType.RESTRICT_MEMBER))
            {
                return new Response(request.getSenderId() ,false ,"you can't remove members of this channel");
            }

            UUID channelId = server.getChannels().get(request.getChannelName());

            //remove user from channel
            database.getChannelOp().updateChannelList(UpdateType.REMOVE , "users" ,
                    channelId.toString() , request.getUserId());

            //remove channel from users list
            updateChannelsUser(request);

            return new Response(request.getSenderId() , true ,
                    "user removed from channel successfully.");
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

    public Response renameChannel(RenameChannelReq request)
    {
        try
        {
            Server server  = database.getServerOp().findByServerId(request.getServerId());

            if(!server.getChannels().containsKey(request.getChannelName()))
            {
                return new Response(request.getSenderId() , false ,
                        "channel not found in server!");
            }

            if(!checkRule(request.getSenderId(), server.getId() , RuleType.RENAME_CHANNEL))
            {
                return new Response(request.getSenderId() ,false ,"you can't rename this channel");
            }

            UUID channelId = server.getChannels().get(request.getChannelName());

            database.getChannelOp().updateChannelConfig(channelId.toString() ,
                    "name" , request.getNewName());

            return new Response(request.getSenderId() , true ,
                    "user renamed successfully.");
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

    private LinkedList<Message> getMessages(LinkedList<UUID> messageIds)
    {
        LinkedList<Message> messages = new LinkedList<>();

        for(UUID id : messageIds)
        {
            try
            {
                messages.add(database.getMessageOp().findById(id.toString()));

            }
            catch (ConfigNotFoundException e)
            {

            }
            catch (IOException | ClassNotFoundException | SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        return messages;
    }

    /**
     * this is used to add channel in users channels list when it added
     */
    private void updateChannelsUser(AddUserChannelReq request) throws ConfigNotFoundException, SQLException, IOException, ClassNotFoundException {

        User user = database.getUserOp().findById(request.getUserId());

        ServerIDs serverId = new ServerIDs(request.getServerId(), new LinkedList<>());

        LinkedList<ServerIDs> serverIDs = user.getServers();

        serverId = serverIDs.get(serverIDs.indexOf(serverId));

        if(null != serverId)
        {
            database.getUserOp().updateList(UpdateType.REMOVE , "servers" , request.getUserId(), serverId);
            serverId.getChannels().add(request.getChannelName());
            database.getUserOp().updateList(UpdateType.ADD , "servers" , request.getUserId(), serverId);
        }
    }

    /**
     * this is used to remove channel of users channel list when it removed
     */
    private void updateChannelsUser(RemoveUserChannelReq request) throws ConfigNotFoundException, SQLException, IOException, ClassNotFoundException {
        User user = database.getUserOp().findById(request.getUserId());

        ServerIDs serverId = new ServerIDs(request.getServerId(), new LinkedList<>());

        LinkedList<ServerIDs> serverIDs = user.getServers();

        serverId = serverIDs.get(serverIDs.indexOf(serverId));

        if(null != serverId)
        {
            database.getUserOp().updateList(UpdateType.REMOVE , "servers" , request.getUserId(), serverId);
            serverId.getChannels().remove(request.getChannelName());
            database.getUserOp().updateList(UpdateType.ADD , "servers" , request.getUserId(), serverId);
        }
    }
}