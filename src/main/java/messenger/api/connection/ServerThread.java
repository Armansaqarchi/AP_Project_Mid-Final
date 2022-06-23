package messenger.api.connection;

import messenger.api.MessageReceiver;
import messenger.api.RequestReceiver;
import messenger.service.model.Transferable;
import messenger.service.model.message.Message;
import messenger.service.model.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable
{
    private MessageReceiver messageReceiver;
    private RequestReceiver requestReceiver;

    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //user id of the client that connected to this socket
    private String id;

    public ServerThread(Socket socket)
    {
        messageReceiver = MessageReceiver.getMessageReceiver();
        requestReceiver = RequestReceiver.getRequestReceiver();

        this.socket =socket;

        try
        {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while(socket.isConnected())
        {
            try
            {
                Transferable input = (Transferable)inputStream.readObject();

                if(input instanceof Message)
                {
                    messageReceiver.getMessage((Message) input);
                }
                else if (input instanceof Request)
                {
                    requestReceiver.getRequest((Request) input);
                }
                else
                {
                    //trow exception about that users inputs invalid object
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        ConnectionHandler.getConnectionHandler().removeConnection(id);
    }

    public void send(Transferable transferable)
    {
        try
        {
            outputStream.writeObject(transferable);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
