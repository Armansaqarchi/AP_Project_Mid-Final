package messenger.api;

import messenger.service.PrivateChatService;
import model.exception.InvalidTypeException;
import model.exception.ServerThreadNotFoundException;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.request.priavteChat.PrivateChatReq;

/**
 * this class gets and handles requests related to private chat
 */
public class PrivateChatApi
{
    private final PrivateChatService service;

    //sender object to send responses to client
    private final Sender sender;

    /**
     * the constructor of class that initializes fields
     */
    protected PrivateChatApi()
    {
        service = new PrivateChatService();
        sender = Sender.getSender();
    }

    /**
     * gets and handles requests
     * @param request the request
     * @throws InvalidTypeException throws it when request's type is invalid
     */
    protected void getRequest(PrivateChatReq request) throws InvalidTypeException {
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
