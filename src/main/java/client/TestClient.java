package client;

import menus.TerminalMenu;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.SignupReq;
import model.response.Response;

import java.util.concurrent.Executors;

public class TestClient
{
    public static void main(String[] args)
    {
        ClientSocket clientSocket = new ClientSocket();

        Executors.newCachedThreadPool().execute(clientSocket);

        TerminalMenu terminalMenu = TerminalMenu.getTerminalMenu(clientSocket);

        terminalMenu.mainMenu();
    }
}
