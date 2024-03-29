package messenger.api;

import messenger.service.ChannelService;
import model.exception.InvalidTypeException;
import model.exception.ServerThreadNotFoundException;
import model.request.Channel.*;
import model.request.server.RemoveRuleReq;
import model.response.Response;

/**
 * this class is used to get and handle requests related to channel
 */
public class ChannelApi
{
    private final ChannelService service;

    //sender object to send responses to client
    private final Sender sender;

    /**
     * the constructor of class
     */
    public ChannelApi()
    {
        service = new ChannelService();
        sender = Sender.getSender();
    }

    /**
     * gets and handles requests
     * @param request the request
     * @throws InvalidTypeException throws when invalid request being sent to this mehod
     */
    protected void getRequest(ChannelReq request) throws InvalidTypeException {
        switch(request.subType())
        {
            case REMOVE_USER -> removeUser((RemoveUserChannelReq) request);
            case ADD_USER -> addUser((AddUserChannelReq) request);
            case PIN_MESSAGE -> pinMessage((PinMessageReq) request);
            case CREAT_CHANNEL -> creatChannel((CreateChannelReq) request);
            case DELETE_CHANNEL -> deleteChannel((DeleteChannelReq) request);
            case RENAME_CHANNEL -> renameChannel((RenameChannelReq) request);
            case GET_CHAT_HISTORY -> getChatHistory((GetChatHistoryReq) request);
            case GET_PINNED_MESSAGES -> getPinnedMessage((GetPinnedMsgReq) request);
            case UN_PIN_MESSAGE -> unPinMessage((UnpinMessageReq) request);
            default -> throw new InvalidTypeException();
        }
    }
    private void addUser(AddUserChannelReq request)
    {
        sendResponse(service.addUser(request));
    }

    private void creatChannel(CreateChannelReq request)
    {
        sendResponse(service.creatChannel(request));
    }

    private void deleteChannel(DeleteChannelReq request)
    {
        sendResponse(service.deleteChannel(request));
    }

    private void getChatHistory(GetChatHistoryReq request)
    {
        sendResponse(service.getChatHistory(request));
    }

    private void pinMessage(PinMessageReq request)
    {
        sendResponse(service.pinMessage(request));
    }

    private void unPinMessage(UnpinMessageReq request)
    {
        sendResponse(service.unPinMessage(request));
    }

    private void getPinnedMessage(GetPinnedMsgReq request)
    {
        sendResponse(service.getPinnedMessage(request));
    }

    private void removeUser(RemoveUserChannelReq request)
    {
        sendResponse(service.removeUser(request));
    }

    private void renameChannel(RenameChannelReq request)
    {
        sendResponse(service.renameChannel(request));
    }

    private void sendResponse(Response response)
    {
        try
        {
            sender.sendResponse(response);
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
