package client;

import client.controller.fxController.cell.testFx;
import client.menus.TerminalMenu;

import java.util.Scanner;
import java.util.concurrent.Executors;

public class Client
{
    public static void main(String[] args)
    {
        if(1 == clientMode())
        {
            testFx.main(args);
            return;
        }
        ClientSocket clientSocket = ClientSocket.getClientSocket();

        Executors.newCachedThreadPool().execute(clientSocket);

        TerminalMenu terminalMenu = TerminalMenu.getTerminalMenu(clientSocket);

        terminalMenu.connectionMenu();
    }

    private static int clientMode()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("choose client mode :");
            System.out.println("[1] - fx");
            System.out.println("[2] - terminal");

            switch (scanner.nextLine())
            {
                case "1" :
                {
                    return 1;
                }
                case "2" :
                {
                    return 2;
                }
            }
        }
    }
}
