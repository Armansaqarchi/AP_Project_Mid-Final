package messenger.service;

import messenger.api.MessageApi;
import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import model.exception.ConfigNotFoundException;
import model.message.FileMessage;
import model.message.FileMsgNotification;
import model.message.Message;
import model.request.GetFileMsgReq;
import model.response.GetFileMsgRes;
import model.response.Response;
import model.server.Channel;
import model.server.Server;
import model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

/**
 * this class gets and handles requests related to message
 */
public class MessageService
{
    private final Database database;
    private final MessageApi messageApi;

    /**
     * constructor of class that need a messageApi object
     * @param messageApi the messageApi object
     */
    public MessageService(MessageApi messageApi)
    {
        database = Database.getDatabase();
        this.messageApi = messageApi;
    }

    /**
     * this method gets and returns unread messages of a user;
     *
     * @param id the user id
     */
    public LinkedList<Message> getUnreadMessage(String id)
    {
        try
        {
            User user = database.getUserOp().findById(id);
            return getMessages(user.getUnreadMessages() , id);
        }

        catch (ConfigNotFoundException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();

            throw new RuntimeException("user not found!");
        }
        catch (IOException | ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method adds message to unread messages list of user
     * @param messageId id of the message
     * @param id id of user
     */
    public void addUnreadMessage(String id , UUID messageId)
    {
        try
        {
            database.getUserOp().updateList(UpdateType.ADD ,
                    "unread_messages" , id , messageId);
        }
        catch (ConfigNotFoundException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method removes message from unread messages list of user
     * @param messageId id of the message
     * @param id id of user
     */
    private void removeUnreadMessage(String id , UUID messageId)
    {
        try
        {
            database.getUserOp().updateList(UpdateType.REMOVE ,
                    "unread_messages" , id , messageId);
        }
        catch (ConfigNotFoundException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    private LinkedList<Message> getMessages(LinkedList<UUID> messageIds , String userId)
    {
        LinkedList<Message> messages = new LinkedList<>();

        for(UUID id : messageIds)
        {
            try
            {
                Message message = database.getMessageOp().findById(id.toString());

                //if message was file message only a notification will be sent to receivers
                // , not whole the file
                if(message instanceof FileMessage)
                {
                    message = new FileMsgNotification((FileMessage) message);
                }

                messages.add(message);
                //remove extracted message from unread messages list
                removeUnreadMessage(userId, message.getId());

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
     * handles channel message
     * @param message the message that sends to chanenl
     * @return response of message
     */
    public Response handleChannelMessage(Message message)
    {
        String[] ides = message.getReceiverId().split("-");

        String serverId = ides[0];
        String channelName = ides[1];

        try
        {
            Server server = database.getServerOp().findByServerId(serverId);

            UUID channelId = server.getChannels().get(channelName);


            Channel channel =
                    database.getChannelOp().findById(channelId.toString());

            //add message to channel's messages
            database.getChannelOp().updateChannelList(UpdateType.ADD ,
                    "messages" , channelId.toString() , message.getId().toString());

            //insert message in database
            database.getMessageOp().insertMessage(message);

            sendMessage(message , channel.getUsers());

            return new Response(message.getSenderId() ,
                    true , "Message sent successfully.");

        }
        catch (ConfigNotFoundException e)
        {
            return new Response(message.getSenderId() ,
                    false , e.getMessage());
        }
        catch (IOException | ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * handles message that being sent in private chat
     * @param message the message
     * @return response of message
     */
    public Response handlePrivateMessage(Message message)
    {
        String privateChatId;

        if(message.getSenderId().compareTo(message.getReceiverId()) < 0)
        {
            privateChatId = message.getSenderId() + '-' + message.getReceiverId();
        }
        else
        {
            privateChatId = message.getReceiverId() + '-' + message.getSenderId();
        }

        try
        {
            User user = database.getUserOp().findById(message.getReceiverId());

            if(user.getBlockedUsers().contains(message.getSenderId()))
            {
                return new Response(message.getSenderId(), false ,
                        "you are blocked by receiver.");
            }

            if(!database.getPrivateChatOp().isExists(privateChatId))
            {
                database.getPrivateChatOp().insertPrivateMessage(privateChatId);

                database.getUserOp().updateList(UpdateType.ADD , "private_chats" , message.getReceiverId() , privateChatId);

                database.getUserOp().updateList(UpdateType.ADD , "private_chats" , message.getSenderId() , privateChatId);
            }

            //insert message in private chats list
            database.getPrivateChatOp().updatePrivateChat(UpdateType.ADD ,
                    "messages",privateChatId , message.getId());

            //insert message in database
            database.getMessageOp().insertMessage(message);

            //sent message
            sendMessage(message , message.getReceiverId());

            return new Response(message.getSenderId() , true ,
                    "message sent successfully.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(message.getSenderId(), false ,
                    e.getMessage());
        }
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    private void sendMessage(Message message , LinkedList<String> receivers)
    {
        //if message was file message only a notification will be sent to receivers
        // , not whole the file
        if(message instanceof FileMessage)
        {
            message = new FileMsgNotification((FileMessage) message);
        }

        for(String receiver : receivers)
        {
            messageApi.sendMessage(message , receiver);
        }
    }

    private void sendMessage(Message message , String receiver)
    {
        //if message was file message only a notification will be sent to receivers
        // , not whole the file
        if(message instanceof FileMessage)
        {
            message = new FileMsgNotification((FileMessage) message);
        }

        messageApi.sendMessage(message , receiver);
    }

    /**
     * handles request to get file message
     * @param request the getting file message request
     * @return response of request
     */
    public Response getFileMsg(GetFileMsgReq request)
    {
        try
        {
            FileMessage message = (FileMessage) database.getMessageOp().
                    findById(request.getMessageId().toString());

            return new GetFileMsgRes(request.getSenderId(), true ,
                    "file sent." , message.getFileName() , message.getContent());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetFileMsgRes(request.getSenderId(), false ,
                    e.getMessage(), null , null);
        }
        catch (ClassCastException e)
        {
            return new GetFileMsgRes(request.getSenderId(), false ,
                    "this message is not file message!" , null  ,  null);
        }
        catch (SQLException | IOException |ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
