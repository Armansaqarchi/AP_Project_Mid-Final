package client.controller.consoleController;

import client.ClientSocket;
import menus.TerminalMenu;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.response.Response;

import java.util.Scanner;

public class SignInController extends InputController {

    //essential fields to sign in
    private String id;
    private String password;

    public SignInController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    /**
     * get user details and sends the sign in request to the server
     * @return true if it was successful.
     * @author Arman sagharchi
     */
    public boolean getUserDetails(){

        try {
            Scanner scanner = new Scanner(System.in);

            //takes the needs to send the sign in req
            System.out.println("in order to be back, press '-0' ");
            System.out.println("Please enter id and password: ");
            System.out.print("[1] id : ");
            this.id = scanner.nextLine();

            //if user wants to be back
            if (this.id.equals("-0")) {
                return false;
            }

            System.out.print("[2] password : ");
            this.password = scanner.nextLine();

            if (this.password.equals("-0")) {
                return false;
            }

            //sets the new id for client
            clientSocket.setId(id);

            //sends the req
            clientSocket.send(new LoginReq(clientSocket.getId(), id,
                    password, null));
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            if(response.isAccepted()) return true;
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return false;

    }
}
