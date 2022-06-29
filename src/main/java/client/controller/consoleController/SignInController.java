package client.controller.consoleController;

import client.ClientSocket;
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

    public void getUserDetails(){

        id = null;
        password = null;

        while(true) {
            try {
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

                clientSocket.send(new LoginReq(clientSocket.getId(), id,
                        password, null));
                Response response = clientSocket.getReceiver().getResponse();

                if(response.isAccepted()){
                    //go to the main menu in discord
                }
                else{
                    System.out.println(response.getMessage());
                }

            }
            catch(ResponseNotFoundException e){
                System.out.println(e.getMessage());
            }

        }

    }
}
