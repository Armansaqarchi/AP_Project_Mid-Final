package messenger.api.connection;


import messenger.api.Receiver;
import model.Transferable;
import model.exception.InvalidObjectException;
import model.exception.InvalidTypeException;
import model.request.Authentication.AuthenticationReq;
import model.request.Authentication.LoginReq;
import model.request.Authentication.SignupReq;
import model.user.UserStatus;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread implements Runnable
{

    private final Receiver receiver;

    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //user id of the client that connected to this socket
    private String id;

    /**
     * the constructor of class
     * @param socket the socket that connected to client
     */
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

    /**
     * this method gets objects from client
     */
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
                if(input instanceof SignupReq || input instanceof LoginReq)
                {
                    ((AuthenticationReq) input).setServerThread(this);
                }

                receiver.receive(input);
            }
            catch (SocketException | EOFException e)
            {
                ConnectionHandler.getConnectionHandler().removeConnection(id);

                //user status must turn to offline in this line
                receiver.turnUserStatus(id , UserStatus.OFFLINE);
                return;
            }
            catch (IOException | ClassNotFoundException |
                   InvalidTypeException | InvalidObjectException e)
            {
                e.printStackTrace();
                return;
            }
        }

        ConnectionHandler.getConnectionHandler().removeConnection(id);

        //user status must turn to offline in this line
        receiver.turnUserStatus(id , UserStatus.OFFLINE);
    }

    /**
     * sends a transferable object to client
     * @param transferable the object that must be sent to client
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

    /**
     * it used when authentication is accepted
     * sets client's id and looks for its messages
     * @param id the verified id
     */
    public void verified(String id)
    {
        //setting verified id
        setId(id);

        //turn user status to online
        receiver.turnUserStatus(id , UserStatus.ONLINE);

        //look for messages of user
        receiver.getUnreadMessages(id);
    }

    /**
     * sets the serverThreads id
     * @param id the id
     */
    public void setId(String id)
    {
        this.id = id;
    }
}
