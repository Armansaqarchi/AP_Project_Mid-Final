package client;

import messenger.service.model.Transferable;
import messenger.service.model.exception.InvalidObjectException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket implements Runnable
{
    Receiver receiver;

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    @Override
    public void run()
    {
        while(socket.isConnected())
        {
            try
            {
                receiver.getInput((Transferable) inputStream.readObject());
            }
            catch (InvalidObjectException | IOException
                   | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
}
