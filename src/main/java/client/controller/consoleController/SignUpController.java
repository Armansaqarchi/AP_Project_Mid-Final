package client.controller.consoleController;

import client.ClientSocket;
import client.controller.InfoVerifier;
import model.exception.*;
import model.request.Authentication.SignupReq;
import model.response.Response;

import java.util.Scanner;

public class SignUpController extends InputController {

    Scanner scanner;

    //essential fields to sign up
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


    /**
     * this method takes all the user details and sends the signUp request to the server
     * @return true if the registering was successful
     * @author Arman sagharchi
     */
    public boolean getUserInfo(){



        //takes the id
        System.out.println("Please enter info's needed to register : ");
        getId();
        if(id == null){
            return false;
        }

        //takes the name
        getName();
        if(name == null){
            return false;
        }

        //takes the password
        getPassword();
        if(password == null){
            return false;
        }

        //takes the email
        getEmail();
        if(email == null){
            return false;
        }


        //takes the phone number
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

        clientSocket.setId(id);

        //sends the related req to the server
        clientSocket.send(new SignupReq(clientSocket.getId(), id, password,
                null, name, email, phoneNumber, null));
        try {
            //takes the response from server about whether it was successful
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
            if(response.isAccepted()) return true;
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return false;
    }


    /**
     * takes the id from user to sign up
     * @author Arman sagharchi
     */
    private void getId(){

        while(true)
        {
            try {
                System.out.println("enter id : ");
                System.out.println("enter '-0' in order to be back");
                id = scanner.nextLine();
                if (id.equals("-0")) {
                    id = null;
                    return;
                }
                InfoVerifier.checkUserValidity(id);

                return;
            }
            catch(InvalidUsernameException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * takes the name from client
     * @author Arman sagharchi
     */
    private void getName(){

        //stuck in a loop till the client wants to be back or
        //enters the info


        System.out.println("enter name : ");
        System.out.println("enter '-0' in order to be back");
        name = scanner.nextLine();
        if (name.equals("-0")) {
            name = null;
        }


    }

    /**
     * takes the password from client
     * and also checks the format of password
     * it must have an upper case and a lower case and also a number
     * @author Arman sagharchi
     */
    private void getPassword(){
        while(true) {
            try {
                //takes the password
                System.out.println("enter password : ");
                System.out.println("enter '-0' in order to be back");
                password = scanner.nextLine();

                if (password.equals("-0")) {
                    password = null;
                    return;
                }
                //takes the password again for preventing any mismatch and incorrect inputting
                //from client
                System.out.print("confirm password : ");
                String confirmPassword = scanner.nextLine();
                if (confirmPassword.equals("-0")) {
                    password = null;
                    return;
                }

                if (password.equals(confirmPassword)) {
                    //checks the validity
                    InfoVerifier.checkPasswordValidity(password);
                    return;
                } else {
                    System.out.println("\033[0;31mconfirm password doesnt match password, try again.\033[0m");
                }
            } catch (InvalidPasswordException e) {
                System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
            }
        }
    }

    /**
     * takes the email from client
     * and checks the validity of email
     * it should have a valid domain like @gmail.com
     * @author Arman sagharchi
     */
    private void getEmail(){


        while(true) {
            try {
                //takes the needs from client
                System.out.println("enter email : ");
                System.out.println("enter '1' in order to be back");
                email = scanner.nextLine();
                if (email.equals("1")) {
                    email = null;
                    return;
                }
                //checks the validity of email
                InfoVerifier.checkEmailValidity(email);
                return;

            } catch (InvalidEmailFormatException e) {
                System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
            }
        }


    }

    /**
     * takes the phone number and also checks the format of phone number
     * it should start with 09... and exactly has 11 numbers
     * @author Arman sagharchi
     */
    private void getPhoneNumber() {

        while (true) {
            try {
                System.out.println("enter phone number :");
                System.out.println("do not want to enter it yet? enter '-0' ");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.equals("-0")) {
                    phoneNumber = null;
                    return;
                }
                InfoVerifier.checkPhoneNumberValidity(phoneNumber);
                return;
            } catch (InvalidPhoneNumberException e) {
                phoneNumber = null;
                System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
            }
        }
    }
}
