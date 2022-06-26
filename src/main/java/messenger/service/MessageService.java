package messenger.service;

import messenger.api.MessageApi;
import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import messenger.service.model.PrivateChat;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.response.Response;
import messenger.service.model.server.Channel;
import messenger.service.model.server.Server;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class MessageService
{
    private final Database database;
    private final MessageApi messageApi;

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

                if(null != message)
                {
                    messages.add(message);

                    //remove extracted message from unread messages list
                    removeUnreadMessage(userId, message.getId());
                }

            }
            catch (IOException | ClassNotFoundException | SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        return messages;
    }

    private Response handleChannelMessage(Message message)
    {
        String[] ides = message.getReceiverId().split("-");

        String serverId = ides[0];
        String channelName = ides[1];

        try
        {
            Server server = database.getServerOp().findByServerId(serverId);

            UUID channelId = server.getChannels().get(channelName);

            try
            {
                Channel channel =
                        database.getChannelOp().findById(channelId.toString());

                LinkedList<String> receivers = channel.getUsers();

                for(String receiver : receivers)
                {
                    messageApi.sendMessage(message , receiver);
                }

                return new Response(message.getSenderId() ,
                        true , "Message sent successfully.");
            }
            catch (ConfigNotFoundException e)
            {
                return new Response(message.getSenderId() ,
                        false , e.getMessage());
            }
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

    private Response handlePrivateMessage(Message message)
    {
        try
        {
            User user = database.getUserOp().findById(message.getReceiverId());

            String privateChatId;

            if(message.getSenderId().compareTo(message.getReceiverId()) < 0)
            {
                privateChatId = message.getSenderId() + '-' + message.getReceiverId();
            }
            else
            {
                privateChatId = message.getReceiverId() + '-' + message.getSenderId();
            }

            PrivateChat privateChat = database.getPrivateChatOp().
                    findById(privateChatId);

            database.getPrivateChatOp().insertPrivateMessage(message.getId());

            messageApi.sendMessage(message , message.getReceiverId());

            return new Response(message.getSenderId() , true ,
                    "message sent successfully.");
        }
        catch (ConfigNotFoundException e)
        {

        }
        catch (IOException | ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
