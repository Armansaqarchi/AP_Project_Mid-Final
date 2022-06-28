package messenger;

import messenger.api.connection.ConnectionHandler;


public class ServerMain
{
    public static void main(String[] args)
    {
        ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();

        connectionHandler.run();
    }
}
