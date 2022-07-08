package client;

import client.menus.TerminalMenu;

import java.util.concurrent.Executors;

public class Client
{
    public static void main(String[] args)
    {
        ClientSocket clientSocket = ClientSocket.getClientSocket();

        Executors.newCachedThreadPool().execute(clientSocket);

        TerminalMenu terminalMenu = TerminalMenu.getTerminalMenu(clientSocket);

        terminalMenu.connectionMenu();
    }
}
