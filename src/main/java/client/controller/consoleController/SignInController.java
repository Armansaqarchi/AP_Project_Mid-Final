package client.controller.consoleController;

import client.ClientSocket;
import menus.TerminalMenu;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.response.Response;

import java.util.Scanner;

public class SignInController extends InputController {

    private String id;
    private String password;

    public SignInController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    public boolean getUserDetails(){

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("in order to be back, press '-0' ");
            System.out.println("Please enter id and password: ");
            System.out.print("[1] id : ");
            this.id = scanner.nextLine();

            if (this.id.equals("-0")) {
                return false;
            }

            System.out.print("[2] password : ");
            this.password = scanner.nextLine();

            if (this.password.equals("-0")) {
                return false;
            }

            clientSocket.setId(id);

            clientSocket.send(new LoginReq(clientSocket.getId(), id,
                    password, null));
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getMessage());

            if(response.isAccepted()) return true;
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return false;

    }
}
