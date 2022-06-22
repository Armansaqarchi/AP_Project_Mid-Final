package messenger.api;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionHandler
{
    //connection of clients
    //maps usernames to server threads of each client
    HashMap<String , ServerThread> connections;

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
}
