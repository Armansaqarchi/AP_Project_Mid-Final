package client;

import messenger.service.model.Transferable;
import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.ResponseNotFoundException;
import messenger.service.model.response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket implements Runnable
{
    private final Receiver receiver;

    private String id;

    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;


    public ClientSocket()
    {
        receiver = new Receiver();

        try
        {
            socket = new Socket("127.0.0.1" , 8080);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Receiver getReceiver() {
        return receiver;
    }

}
