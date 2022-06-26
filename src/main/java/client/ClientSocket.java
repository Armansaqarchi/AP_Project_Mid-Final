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

    private String id;
    private String  password;

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public ClientSocket(){
        id = null;
        password = null;

        //other assignments including inputSt and outputSt should be implemented here
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

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
