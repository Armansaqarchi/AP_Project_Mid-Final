package client.controller.consoleController;

import client.ClientSocket;
import client.controller.InfoVerifier;
import model.exception.*;
import model.request.Authentication.SignupReq;
import model.response.Response;

import java.util.Scanner;

public class SignUpController extends InputController {

    Scanner scanner;


    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;



    public SignUpController(ClientSocket clientSocket){
        super(clientSocket);

        scanner = new Scanner(System.in);
        name = null;
        password = null;
        email = null;
        phoneNumber = null;
    }



    public void getUserInfo(){


        System.out.println("Please enter info's needed to register : ");
        getId();
        if(id == null){
            return;
        }


        getUserName();
        if(name == null){
            return;
        }

        getPassword();
        if(password == null){
            return;
        }


        getEmail();
        if(email == null){
            return;
        }


        System.out.println("do you want to enter phone number ? ");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int choice = getOptionalInput(1, 2);

        if(choice == 1){
            getPhoneNumber();
        }
        else{
            phoneNumber = null;
        }


        clientSocket.send(new SignupReq(clientSocket.getId(), id, password,
                null, name, email, phoneNumber, null));
        try {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getMessage());
        }
        catch(ResponseNotFoundException e){
            System.err.println(e.getMessage());
        }


    }

    private void getId(){

        System.out.println("enter id : ");
        System.out.println("enter '-0' in order to be back");
        id = scanner.nextLine();
        if(id.equals("-0")){
            id = null;
        }
    }




    private void getUserName(){


        try {
            System.out.println("enter name : ");
            System.out.println("enter '-0' in order to be back");
            name = scanner.nextLine();
            if(name.equals("-0")){
                password = null;
                return;
            }
            InfoVerifier.checkUserValidity(name);

        }
        catch(InvalidUsernameException e){
            System.err.println(e.getMessage());
        }

    }

    private void getPassword(){
        try{
            System.out.println("enter password : ");
            System.out.println("enter '-0' in order to be back");
            password = scanner.nextLine();

            if(password.equals("-0")){
                password = null;
                return;
            }
            System.out.print("confirm password : ");
            String confirmPassword = scanner.nextLine();

            if(password.equals(confirmPassword)) {
                InfoVerifier.checkPasswordValidity(password);
            }
        }
        catch(InvalidPasswordException e){
            System.err.println(e.getMessage());
        }
    }

    private void getEmail(){


        try{
            System.out.println("enter email : ");
            System.out.println("enter '1' in order to be back");
            email = scanner.nextLine();
            if(email.equals("1")){
                email = null;
                return;
            }
            InfoVerifier.checkEmailValidity(email);

        }
        catch(InvalidEmailFormatException e){
            System.err.println(e.getMessage());
        }


    }

    private void getPhoneNumber(){

        try{
            System.out.println("enter phone number :");
            System.out.println("do not want to enter it yet? enter '-0' ");
            phoneNumber = scanner.nextLine();
            if(phoneNumber.equals("-0")){
                phoneNumber = null;
                return;
            }
            InfoVerifier.checkPhoneNumberValidity(phoneNumber);
            }
        catch(InvalidPhoneNumberException e){
            System.err.println(e.getMessage());
        }
    }
}
