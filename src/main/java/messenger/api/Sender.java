package messenger.api;

import messenger.service.model.message.Message;
import messenger.service.model.response.Response;

/**
 * this class is used to send message or responses to client
 *
 * @author mahdi
 */
public class Sender
{

    //returns that message is sent or not (user is online or not)
    public boolean sendMessage(Message message)
    {

        return false;
    }

    //it may return a value to determine that response is sent or not
    public void sendResponse(Response response)
    {

    }
}
