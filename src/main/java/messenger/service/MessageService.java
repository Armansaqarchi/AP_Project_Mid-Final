package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.response.Response;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class MessageService
{
    private final Database database;

    public MessageService()
    {
        database = Database.getDatabase();
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

        return null;
    }
}
