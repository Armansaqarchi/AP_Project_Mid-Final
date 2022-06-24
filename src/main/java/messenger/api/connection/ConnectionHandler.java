package messenger.api.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionHandler
{
    private static ConnectionHandler connectionHandler;

    //connection of clients
    //maps usernames to server threads of each client
    private HashMap<String , ServerThread> connections;

    private ConnectionHandler()
    {

    }

    public static ConnectionHandler getConnectionHandler()
    {
        if(null == connectionHandler)
        {
            connectionHandler = new ConnectionHandler();
        }

        return connectionHandler;
    }

    public void run()
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

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

    public ServerThread getServerThread(String id)
    {
        return connections.get(id);
    }
    public void addConnection(String id , ServerThread serverThread)
    {
        connections.put(id, serverThread);
    }

    public void removeConnection(String id)
    {
        connections.remove(id);
    }
}
