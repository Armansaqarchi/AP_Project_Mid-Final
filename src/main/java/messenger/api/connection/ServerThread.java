package messenger.api.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable
{
    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //user id of the client that connected to this socket
    private String id;

    public ServerThread(Socket socket)
    {
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

    }
}
