package client.controller.consoleController;

import client.ClientSocket;
import client.controller.InfoVerifier;
import model.exception.InvalidEmailFormatException;
import model.exception.InvalidPasswordException;
import model.exception.InvalidPhoneNumberException;
import model.exception.InvalidUsernameException;
import model.request.Authentication.SignupReq;

import java.util.Scanner;

public class SignUpController extends InputController {
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

        id = getId();
        if(id.equals("1")){
            // return ro the main menu
        }


        name = getName();
        if(name.equals("1")){
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
                phoneNumber = getPhoneNumber();
                break;
            case 2:
                break;
        }

        clientSocket.send(new SignupReq(clientSocket.getId(), id, password,
                null, name, email, phoneNumber, null));


    }

    private String getId(){

        System.out.println("enter id : ");
        System.out.println("enter '1' in order to be back");
        id = scanner.nextLine();

        return id;
    }




    private String getName(){

        while(true){
            try {
                System.out.println("enter name : ");
                System.out.println("enter '1' in order to be back");
                name = scanner.nextLine();
                if(name.equals("1")){
                    return "1";
                }
                InfoVerifier.checkUserValidity(name);
                break;
            }
            catch(InvalidUsernameException e){
                System.err.println(e.getMessage());
            }
        }

        return name;
    }

    private String getPassword()
    {

        while(true)
        {
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
