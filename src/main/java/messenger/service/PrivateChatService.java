package messenger.service;

import messenger.dataBaseOp.Database;
import model.PrivateChat;
import model.exception.ConfigNotFoundException;
import model.message.FileMessage;
import model.message.FileMsgNotification;
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
        if(!database.getUserOp().isExists(request.getUserId()))
        {
            return new GetPrivateChatHisRes(request.getSenderId() , false ,
                    "user with id : '" + request.getUserId() + "' is not exists." ,
                    new LinkedList<>());
        }

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
                Message message = database.getMessageOp().findById(id.toString());

                //if message was file message only a notification will be sent to receivers
                // , not whole the file
                if(message instanceof FileMessage)
                {
                    message = new FileMsgNotification((FileMessage) message);
                }

                messages.add(message);

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
