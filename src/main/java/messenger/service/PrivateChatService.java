package messenger.service;

import messenger.dataBaseOp.Database;
import model.PrivateChat;
import model.exception.ConfigNotFoundException;
import model.message.Message;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.response.privateChat.GetPrivateChatHisRes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class PrivateChatService
{
    private Database database;

    public PrivateChatService()
    {
        database = Database.getDatabase();
    }

    public GetPrivateChatHisRes getChatHistory(GetPrivateChatHisReq request)
    {
        String privateChatId;

        if(request.getSenderId().compareTo(request.getUserId()) < 0)
        {
            privateChatId = request.getSenderId() + '-' + request.getUserId();
        }
        else
        {
            privateChatId = request.getUserId() + '-' + request.getSenderId();
        }

        try
        {
            PrivateChat privateChat = database.getPrivateChatOp().findById(privateChatId);

            return new GetPrivateChatHisRes(request.getSenderId(), true ,
                    "request accepted." , getMessages(privateChat.getMessages()));
        }
        catch (ConfigNotFoundException e)
        {
            return new GetPrivateChatHisRes(request.getSenderId() , false ,
                    "you have not messaged to '" + request.getUserId() + "' yet." ,
                    new LinkedList<>());
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
}
