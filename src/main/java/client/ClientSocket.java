package client;

import model.Transferable;
import model.exception.InvalidObjectException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket implements Runnable
{
    //has a receiver which receives responses from server

    private static ClientSocket clientSocket;


    private final Receiver receiver;

    //client id
    private String id;

    //socket between client and server
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;


    private ClientSocket()
    {
        //setting primary configs for client
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


    public static ClientSocket getClientSocket(){
        if(clientSocket == null)
        {
            clientSocket = new ClientSocket();
        }

        return clientSocket;
    }

    /**
     * this method is overRide method of runnable interface
     * this is used to take messages and requests coming from the server
     */
    @Override
    public void run()
    {
        //beside the main thread, client has a second thread
        // which checks the responses coming from server
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

    /**
     * takes a transferable and sends it to the server
     * @param transferable, the req
     * @author mahdi kalhor
     */
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
