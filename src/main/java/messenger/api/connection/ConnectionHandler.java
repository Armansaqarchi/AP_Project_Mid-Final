package messenger.api.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class handles and stores connections with clients
 * and assign's a server thread to each connection
 */
public class ConnectionHandler
{
    private static ConnectionHandler connectionHandler;

    //connection of clients
    //maps usernames to server threads of each client
    private HashMap<String , ServerThread> connections;

    /**
     * the constructor of class
     */
    private ConnectionHandler()
    {
        connections = new HashMap<>();
    }

    /**
     * this method returns the connection handler object
     * @return the connection handler
     */
    public static ConnectionHandler getConnectionHandler()
    {
        if(null == connectionHandler)
        {
            connectionHandler = new ConnectionHandler();
        }

        return connectionHandler;
    }

    /**
     * runs the server and waits for client connection
     */
    public void run()
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("server is running on port : 8080\n");

        try(ServerSocket socket = new ServerSocket(8080);)
        {
            while(true)
            {
                Socket clientSocket = socket.accept();

                ServerThread serverThread = new ServerThread(clientSocket);

                executorService.execute(serverThread);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * returns server thread of an id if its exists
     * @param id the id of user
     * @return returns the server thread of the id if its exists
     */
    public ServerThread getServerThread(String id)
    {
        return connections.get(id);
    }

    /**
     * adds a connection to hashMap
     * @param id the users id
     * @param serverThread the server thread that connected to this user
     */
    public void addConnection(String id , ServerThread serverThread)
    {
        connections.put(id, serverThread);
        System.out.println("connection with id :" + id + " added.");
    }

    /**
     * removes a connection
     * @param id the users id
     */
    public void removeConnection(String id)
    {
        connections.remove(id);
        System.out.println("connection with id :" + id + " removed.");
    }
}
