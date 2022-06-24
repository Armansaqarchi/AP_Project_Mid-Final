package messenger.api;

import messenger.api.connection.ConnectionHandler;
import messenger.api.connection.ServerThread;
import messenger.service.model.exception.ServerThreadNotFoundException;
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
    public void sendMessage(String receiverId , Message message)
            throws ServerThreadNotFoundException
    {
        ServerThread serverThread = ConnectionHandler.
                getConnectionHandler().getServerThread(receiverId);

        if(null != serverThread)
        {
            serverThread.send(message);
        }
        else
        {
            throw new ServerThreadNotFoundException();
        }

    }

    //it may return a value to determine that response is sent or not
    public void sendResponse(Response response) throws ServerThreadNotFoundException {
        ServerThread serverThread = ConnectionHandler.
                getConnectionHandler().getServerThread(response.getReceiverId());

        if(null != serverThread)
        {
            serverThread.send(response);
        }
        else
        {
            throw new ServerThreadNotFoundException();
        }
    }
}
