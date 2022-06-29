package messenger.api;

import messenger.api.connection.ConnectionHandler;
import messenger.api.connection.ServerThread;
import model.exception.ServerThreadNotFoundException;
import model.message.Message;
import model.response.Response;

/**
 * this class is used to send message or responses to client
 *
 * @author mahdi
 */
public class Sender
{

    private ConnectionHandler connectionHandler;

    private static Sender sender;

    protected static Sender getSender()
    {
        if(null == sender)
        {
            sender = new Sender();
        }

        return sender;
    }
    private Sender()
    {
        connectionHandler =ConnectionHandler.getConnectionHandler();
    }

    //returns that message is sent or not (user is online or not)
    public void sendMessage(Message message , String receiverId)
            throws ServerThreadNotFoundException
    {
        ServerThread serverThread = connectionHandler.getServerThread(receiverId);

        if(null != serverThread)
        {
            serverThread.send(message);
        }
        else
        {
            throw new ServerThreadNotFoundException("failed to send message , server thread not found.");
        }

    }

    //it may return a value to determine that response is sent or not
    public void sendResponse(Response response) throws ServerThreadNotFoundException {
        ServerThread serverThread = connectionHandler.getServerThread(response.getReceiverId());

        if(null != serverThread)
        {
            serverThread.send(response);
        }
        else
        {
            throw new ServerThreadNotFoundException("failed to send response , server thread not found.");
        }
    }

    public void sendResponse(Response response , ServerThread serverThread) throws ServerThreadNotFoundException {
        if(null != serverThread)
        {
            serverThread.send(response);
        }
        else
        {
            throw new ServerThreadNotFoundException("failed to send response , server thread not found.");
        }
    }
}
