package controller.consoleController;

import client.ClientSocket;
import controller.InfoVerifier;
import messenger.service.model.exception.InvalidEmailFormatException;
import messenger.service.model.exception.InvalidPasswordException;
import messenger.service.model.exception.InvalidPhoneNumberException;
import messenger.service.model.exception.InvalidUsernameException;
import messenger.service.model.request.Authentication.AuthenticationReqType;
import messenger.service.model.request.Authentication.SignupReq;

import java.util.Scanner;

public class SignUpController extends InputHandler {

    Scanner scanner;


    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNUmber;



    public SignUpController(){
        scanner = new Scanner(System.in);
        username = null;
        password = null;
        email = null;
        phoneNUmber = null;
    }



    public void getUserInfo(ClientSocket clientSocket){



        System.out.println("Please enter info's needed to register : ");

        id = getId();
        if(id.equals("1")){
            // return ro the main menu
        }


        username = getUserName();
        if(username.equals("1")){
            // return ro the main menu
        }

        password = getPassword();
        if(password.equals("1")){
            //return to the main menu
        }

        email = getEmail();
        if(email.equals("1")){
            //return to the main manu
        }

        System.out.println("do you want to enter phone number ? ");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int choice = getOptionalInput(1, 2);

        switch(choice){
            case 1:
                phoneNUmber = getPhoneNumber();
                break;
            case 2:
                break;
        }

        clientSocket.send(new SignupReq(AuthenticationReqType.SIGNUP, id, password,
                null, username, email, phoneNUmber, null));


    }

    private String getId(){
        String id = "";

        System.out.println("enter id : ");
        System.out.println("enter '1' in order to be back");
        username = scanner.nextLine();
        if(username.equals("1")){
            return "1";
        }

        return id;
    }




    private String getUserName(){
        String username = "";

        while(true){
            try {
                System.out.println("enter username : ");
                System.out.println("enter '1' in order to be back");
                username = scanner.nextLine();
                if(username.equals("1")){
                    return "1";
                }
                InfoVerifier.checkUserValidity(username);
                break;
            }
            catch(InvalidUsernameException e){
                System.err.println(e.getMessage());
            }
        }

        return username;
    }

    private String getPassword(){
        String password = "";

        while(true){
            try{
                System.out.println("enter password : ");
                System.out.println("enter '1' in order to be back");
                password = scanner.nextLine();

                if(password.equals("1")){
                    return "1";
                }
                System.out.print("confirm password : ");
                String confirmPassword = scanner.nextLine();


                if(password.equals(confirmPassword)) {
                    InfoVerifier.checkPasswordValidity(password);
                    break;
                }
            }
            catch(InvalidPasswordException e){
                System.err.println(e.getMessage());
            }
        }

        return password;
    }

    private String getEmail(){
        String email;

        while(true){
            try{
                System.out.println("enter email : ");
                System.out.println("enter '1' in order to be back");
                email = scanner.nextLine();
                if(email.equals("1")){
                    return "1";
                }
                InfoVerifier.checkEmailValidity(email);
                break;
            }
            catch(InvalidEmailFormatException e){
                System.err.println(e.getMessage());
            }
        }

        return email;
    }

    private String getPhoneNumber(){
        String phoneNumber;

        while(true){
            try{
                System.out.println("enter phone number :");
                System.out.println("enter '1' in order to be back");
                phoneNumber = scanner.nextLine();
                if(phoneNumber.equals("1")){
                    return "1";
                }
                InfoVerifier.checkPhoneNumberValidity(phoneNumber);
                break;
            }
            catch(InvalidPhoneNumberException e){
                System.out.println(e.getMessage());
            }
        }

        return phoneNumber;
    }
}
