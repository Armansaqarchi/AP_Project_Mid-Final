package messenger.api;

import messenger.service.MessageService;
import model.exception.InvalidTypeException;
import model.exception.ServerThreadNotFoundException;
import model.message.Message;
import model.request.GetFileMsgReq;
import model.response.Response;

import java.util.LinkedList;

/**
 * gets and handles messages that are sent by client
 */
public class MessageApi
{
    private final MessageService service;
    private final Sender sender;

    /**
     * the constructor that initializes fields of object
     */
    protected MessageApi()
    {
        sender = Sender.getSender();
        service = new MessageService(this);
    }

    /**
     * when client verified , this method looks for unread messages of user
     * and sends unread messages
     * @param id the user id
     */
    protected void getUnreadMessages(String id)
    {
        LinkedList<Message> unReadMessages = service.getUnreadMessage(id);

        for(Message message : unReadMessages)
        {
            sendMessage(message , id);
        }
    }

    /**
     * sends message to client using its id
     * @param message the message
     * @param id receiver's id
     */
    public void sendMessage(Message message , String id)
    {
        try
        {
            sender.sendMessage(message , id);
        }
        catch (ServerThreadNotFoundException e)
        {
            //add message to unread messages list of user, to send later
            service.addUnreadMessage(id , message.getId());
        }
    }

    /**
     * gets a message and handles it
     * @param message the message
     * @throws InvalidTypeException throw's it , if messages type was invalid
     */
    protected void getMessage(Message message) throws InvalidTypeException {
        switch (message.getType())
        {
            case CHANNEL -> getChannelMessage(message);
            case PRIVATE_CHAT -> getPrivateChatMessage(message);
            default -> throw new InvalidTypeException();
        }
    }

    /**
     * used to get a file message
     * @param request the request to get file message
     */
    protected void getFileMsg(GetFileMsgReq request)
    {
        sendResponse(service.getFileMsg(request));
    }
    private void getChannelMessage(Message message)
    {
        sendResponse(service.handleChannelMessage(message));
    }

    private void getPrivateChatMessage(Message message)
    {
        sendResponse(service.handlePrivateMessage(message));
    }


    private void sendResponse(Response response)
    {
        try {
            sender.sendResponse(response);
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
