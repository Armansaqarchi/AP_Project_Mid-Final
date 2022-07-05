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

    /**
     * @return the sender object
     */
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

    /**
     * sends a message to client
     * @param message the message
     * @param receiverId the messages receiver
     * @throws ServerThreadNotFoundException throws if server thread wasn't exists (user was offline)
     */
    protected void sendMessage(Message message , String receiverId)
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


    /**
     * sends a response to client
     * @param response the response
     * @throws ServerThreadNotFoundException throws if server thread wasn't exists (user was offline)
     */
    protected void sendResponse(Response response) throws ServerThreadNotFoundException {
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

    /**
     * sends a response to client
     * @param response the response
     * @param serverThread the receiver's server thread
     * @throws ServerThreadNotFoundException throws if server thread wasn't exists (user was offline)
     */
    protected void sendResponse(Response response , ServerThread serverThread) throws ServerThreadNotFoundException {
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
