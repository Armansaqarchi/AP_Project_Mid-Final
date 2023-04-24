package messenger;

import messenger.api.connection.ConnectionHandler;

/**
 * provides objects to run the server of messenger
 */
public class ServerMain
{
    
    
    /**
     * the main method of messenger's server
     * @param args
     */
    public static void main(String[] args)
    {
        ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();

        connectionHandler.run();
    }
}
