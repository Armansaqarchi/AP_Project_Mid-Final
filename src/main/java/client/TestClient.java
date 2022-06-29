package client;

import client.controller.consoleController.ServerController;
import client.controller.consoleController.SignInController;
import client.controller.consoleController.SignUpController;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.request.Authentication.SignupReq;
import model.response.Response;

import java.util.concurrent.Executors;

public class TestClient
{
    public static void main(String[] args)
    {
        ClientSocket clientSocket = new ClientSocket();

        Executors.newCachedThreadPool().execute(clientSocket);

        SignInController signInController = new SignInController(clientSocket);

        ServerController serverController = new ServerController(clientSocket);

        clientSocket.send(new LoginReq("56" , "56" , "Tt6" , null));

        clientSocket.setId("56");
        try
        {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getReceiverId());
            System.out.println(response.isAccepted());
            System.out.println(response.getMessage());
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        serverController.creatServer();

        try
        {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getReceiverId());
            System.out.println(response.isAccepted());
            System.out.println(response.getMessage());
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
