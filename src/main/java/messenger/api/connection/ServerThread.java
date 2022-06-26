package messenger.api.connection;


import messenger.api.Receiver;
import messenger.service.model.Transferable;
import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.request.Authentication.AuthenticationReq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable
{

    private final Receiver receiver;

    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //user id of the client that connected to this socket
    private String id;

    public ServerThread(Socket socket)
    {
        receiver = Receiver.getReceiver();

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

                //server thread most be saved in authentication request
                //because it does not added to connections before
                if(input instanceof AuthenticationReq)
                {
                    ((AuthenticationReq) input).setServerThread(this);
                }

                receiver.receive(input);
            }
            catch (IOException | ClassNotFoundException |
                   InvalidTypeException | InvalidObjectException e)
            {
                e.printStackTrace();
            }
        }

        ConnectionHandler.getConnectionHandler().removeConnection(id);

        //user status must turn to offline in this line
    }

    public void send(Transferable transferable)
    {
        try
        {
            outputStream.writeObject(transferable);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
