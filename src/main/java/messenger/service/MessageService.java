package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class MessageService
{
    private Database database;

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

            return getMessages(user.getUnreadMessages());
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
     * if sending
     * @param message
     */
    public void addUnreadMessage(Message message , String id)
    {

    }
    private LinkedList<Message> getMessages(LinkedList<UUID> messageIds)
    {
        LinkedList<Message> messages = new LinkedList<>();

        for(UUID id : messageIds)
        {
            try
            {
                Message message = database.getMessageOp().findById(id.toString());

                if(null != message)
                    messages.add(message);

            }
            catch (IOException | ClassNotFoundException | SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        return messages;
    }
}
