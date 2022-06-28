package messenger.api;

import messenger.service.AuthenticationService;
import messenger.service.PrivateChatService;
import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.exception.ServerThreadNotFoundException;
import messenger.service.model.message.FileMessage;
import messenger.service.model.message.Message;
import messenger.service.model.message.TextMessage;
import messenger.service.model.request.priavteChat.GetPrivateChatHisReq;
import messenger.service.model.request.priavteChat.PrivateChatReq;

public class PrivateChatApi
{
    private final PrivateChatService service;

    //sender object to send responses to client
    private final Sender sender;

    /**
     * the constructor of class that initializes fields
     */
    public PrivateChatApi()
    {
        service = new PrivateChatService();
        sender = Sender.getSender();
    }

    public void getRequest(PrivateChatReq request) throws InvalidTypeException {
        switch (request.subType())
        {
            case GET_CHAT_HISTORY -> getChatHistory((GetPrivateChatHisReq) request);

            default -> throw new InvalidTypeException();
        }
    }

    private void getChatHistory(GetPrivateChatHisReq request)
    {
        try
        {
            sender.sendResponse(service.getChatHistory(request));
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
