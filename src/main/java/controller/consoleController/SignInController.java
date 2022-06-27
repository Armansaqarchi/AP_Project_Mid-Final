package controller.consoleController;

import client.ClientSocket;
import messenger.service.model.request.Authentication.AuthenticationReqType;
import messenger.service.model.request.Authentication.LoginReq;

import java.util.Scanner;

public class SignInController extends InputController {

    private String id;
    private String password;

    public SignInController(ClientSocket clientSocket, String id) {
        super(clientSocket);
    }

    public void getUserDetails(ClientSocket clientSocket){



        if(clientSocket.getId() != null && clientSocket.getPassword() != null){
            clientSocket.send(new LoginReq(AuthenticationReqType.LOGIN, clientSocket.getId(),
                     clientSocket.getPassword(), null));

            return;
        }

        while(true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("in order to be back, press '1' ");
            System.out.println("Please enter id and password: ");
            System.out.print("[1] id : ");
            this.id = scanner.nextLine();

            if (this.id.equals("1")) {
                //exit the method and return to the main menu
            }

            System.out.print("[2] password : ");
            this.password = scanner.nextLine();

            if (this.password.equals("1")) {
                //do the same thing
            }

            clientSocket.setId(id);
            clientSocket.setPassword(password);

            clientSocket.send(new LoginReq(AuthenticationReqType.LOGIN, id,
                    password, null));

        }
    }
}
