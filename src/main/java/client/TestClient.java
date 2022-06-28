package client;

import client.controller.consoleController.SignInController;
import client.controller.consoleController.SignUpController;
import messenger.service.model.exception.ResponseNotFoundException;
import messenger.service.model.request.Authentication.SignupReq;
import messenger.service.model.response.Response;

import java.util.concurrent.Executors;

public class TestClient
{
    public static void main(String[] args)
    {
        ClientSocket clientSocket = new ClientSocket();

        Executors.newCachedThreadPool().execute(clientSocket);

//        SignInController signInController = new SignInController(clientSocket);
//
//        signInController.getUserDetails();
        clientSocket.send(new SignupReq("1788", "1", "1",
                null, "1","1", "1", null));


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
