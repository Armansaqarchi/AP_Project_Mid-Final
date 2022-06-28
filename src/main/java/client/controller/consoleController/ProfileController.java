package client.controller.consoleController;

import client.ClientSocket;
import client.controller.InfoVerifier;
import model.exception.InvalidEmailFormatException;
import model.exception.InvalidPasswordException;
import model.exception.InvalidPhoneNumberException;
import model.exception.InvalidUsernameException;
import model.user.UserStatus;

public class ProfileController extends InputController {

    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private UserStatus userStatus;

    private byte[] profileImage;

    public ProfileController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    public void UpdateProfile(){

        id = null;
        name = null;
        password = null;
        email = null;
        phoneNumber = null;
        profileImage = null;
        userStatus = null;



        while(true){

            System.out.println("[1] change id" + "            current : " + id);
            System.out.println("[2] change name" + "          current : " + name);
            System.out.println("[3] change email" + "         current : " + email);
            System.out.println("[4] change password" + "      current : " + password);
            System.out.println("[5] change phone number" + "  current : " + phoneNumber);
            System.out.println("[6] change user status" + "   current : " + userStatus);
            System.out.println("[7] submit");
            System.out.println("[8] back");


            switch(getOptionalInput(1, 8)){
                case 1:
                    changeId();
                    break;
                case 2:
                    changeName();
                    break;
                case 3:
                    changeEmail();
                    break;
                case 4:
                    changePassword();
                    break;
                case 5:
                    changePhoneNumber();
                    break;
                case 6:
                    changeUserStatus();
                    break;
                case 7:
                    //go to the next menu, remember changing the id in client socket when the id changes.
                    break;
                case 8:
                    //back to the previous menu
                    break;
            }

        }
    }


    private void changeId(){
        System.out.println("enter new id : ");
        String newId = scanner.nextLine();

        this.id = id;
    }

    private void changeName(){
        System.out.println("enter new name : ");
        String newName = scanner.nextLine();
        try{
            InfoVerifier.checkUserValidity(newName);
            this.name = name;
        }
        catch(InvalidUsernameException e){
            System.out.println(e.getMessage());
        }

    }

    private void changePassword(){
        System.out.println("enter new password : ");
        String newPass = scanner.nextLine();
        System.out.println("confirm new password : ");
        String conNewPass = scanner.nextLine();

        if(newPass.equals(conNewPass)) {
            try {
                InfoVerifier.checkPasswordValidity(password);
                this.password = password;
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.err.println("there is mismatch between password and confirm password, try again.");
        }
    }

    private void changeEmail(){
        System.out.println("enter new email : ");
        String newEmail = scanner.nextLine();
        try{
            InfoVerifier.checkEmailValidity(newEmail);
            this.email = newEmail;
        }
        catch(InvalidEmailFormatException e){
            System.out.println(e.getMessage());
        }
    }

    private void changeUserStatus(){

        System.out.println("[1] Online");
        System.out.println("[2] Idle");
        System.out.println("[3] Do not disturb");
        System.out.println("[4] back");

        switch (getOptionalInput(1, 4)){
            case 1:
                userStatus = UserStatus.ONLINE;
                break;
            case 2:
                userStatus = UserStatus.IDLE;
                break;
            case 3:
                userStatus = UserStatus.DO_NOT_DISTURB;
                break;
        }
    }

    private void changePhoneNumber(){
        System.out.println("enter new phone number : ");
        String newPhoneNumber = scanner.nextLine();
        try{
            InfoVerifier.checkPhoneNumberValidity(newPhoneNumber);
            this.phoneNumber = newPhoneNumber;
        }
        catch(InvalidPhoneNumberException e){
            System.out.println(e.getMessage());
        }
    }

}
