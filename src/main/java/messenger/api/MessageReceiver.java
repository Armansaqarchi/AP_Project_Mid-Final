package messenger.api;

import messenger.service.model.message.Message;

public class MessageReceiver
{
    private static MessageReceiver messageReceiver;

    private MessageReceiver()
    {

    }

    public static MessageReceiver getMessageReceiver()
    {
        if(null == messageReceiver)
        {
            messageReceiver = new MessageReceiver();
        }

        return messageReceiver;
    }
    public void getMessage(Message Message)
    {

    }
}
