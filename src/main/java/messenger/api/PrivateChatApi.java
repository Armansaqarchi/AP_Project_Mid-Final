package messenger.api;

import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.message.FileMessage;
import messenger.service.model.message.Message;
import messenger.service.model.message.TextMessage;
import messenger.service.model.request.priavteChat.GetPrivateChatHisReq;
import messenger.service.model.request.priavteChat.PrivateChatReq;

public class PrivateChatApi
{
    public void getRequest(PrivateChatReq request) throws InvalidTypeException {
        switch (request.subType())
        {
            case GET_CHAT_HISTORY -> getChatHistory((GetPrivateChatHisReq) request);

            default -> throw new InvalidTypeException();
        }
    }

    public void getMessage(Message message) throws InvalidObjectException {

        if(message instanceof FileMessage)
        {
            getFileMessage((FileMessage) message);
        }
        else if(message instanceof TextMessage)
        {
            getTextMessage((TextMessage) message);
        }
        else
        {
            throw new InvalidObjectException();
        }
    }

    private void getFileMessage(FileMessage message)
    {

    }

    private void getTextMessage(TextMessage message)
    {

    }

    private void getChatHistory(GetPrivateChatHisReq request)
    {

    }
}
