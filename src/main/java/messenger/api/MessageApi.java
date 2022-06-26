package messenger.api;

import messenger.service.MessageService;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.exception.ServerThreadNotFoundException;
import messenger.service.model.message.Message;

import java.util.LinkedList;

public class MessageApi
{
    private MessageService service;
    private Sender sender;

    public MessageApi()
    {
        sender = Sender.getSender();
        service = new MessageService();
    }

    /**
     * when client verified , this method looks for unread messages of user
     * and sends unread messages
     * @param id the user id
     */
    public void getUnreadMessages(String id)
    {
        LinkedList<Message> unReadMessages = service.getUnreadMessage(id);

        for(Message message : unReadMessages)
        {
            try
            {
                sender.sendMessage(message);
            }
            catch (ServerThreadNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void getMessage(Message message) throws InvalidTypeException {
        switch (message.getType())
        {
            case CHANNEL -> getChannelMessage(message);
            case PRIVATE_CHAT -> getPrivateChatMessage(message);
            default -> throw new InvalidTypeException();
        }
    }

    private void getChannelMessage(Message message)
    {

    }

    private void getPrivateChatMessage(Message message)
    {

    }
}
