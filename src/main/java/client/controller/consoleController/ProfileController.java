package client.controller.consoleController;

import client.ClientSocket;
import client.controller.InfoVerifier;
import model.exception.*;
import model.request.user.GetMyProfileReq;
import model.request.user.SetMyProfileReq;
import model.response.Response;
import model.response.user.GetMyProfileRes;
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

    public void updateProfile(){

        id = null;
        name = null;
        password = null;
        email = null;
        phoneNumber = null;
        profileImage = null;
        userStatus = null;

        boolean isRunning = true;

        while(isRunning){

            System.out.println("[1] change id" + "            current : " + id);
            System.out.println("[2] change name" + "          current : " + name);
            System.out.println("[3] change email" + "         current : " + email);
            System.out.println("[4] change password" + "      current : " + password);
            System.out.println("[5] change phone number" + "  current : " + phoneNumber);
            System.out.println("[6] change user status" + "   current : " + userStatus);
            System.out.println("[7] submit");


            switch(getOptionalInput(1, 8)){
                case 1 -> changeId();
                case 2 -> changeName();
                case 3 -> changeEmail();
                case 4 -> changePassword();
                case 5 -> changePhoneNumber();
                case 6 -> changeUserStatus();
                case 7 -> isRunning = false;
            }

            newProfileSender();

        }

    }

    private void newProfileSender(){
        clientSocket.send(new SetMyProfileReq(clientSocket.getId(), id, name,
                password, email, phoneNumber, profileImage, userStatus));
        try {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getMessage());
            if(response.isAccepted()){
                clientSocket.setId(id);
            }

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }


    public void showMyProfile()
    {
        try
        {
            clientSocket.send(new GetMyProfileReq(clientSocket.getId()));

            GetMyProfileRes response = (GetMyProfileRes)clientSocket.getReceiver().getResponse();

            if(response.isAccepted())
            {
                System.out.println(response.getMessage());
                System.out.println(response);

            }
            else{
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }



    private void changeId(){
        System.out.println("enter new id : ");
        id = scanner.nextLine();
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


    private void showProfile(){
        System.out.println("enter your id : ");
        System.out.println("to be back, enter '-0'");
        id = scanner.nextLine();

        if(id.equals("-0")) return;

        try {
            clientSocket.send(new GetMyProfileReq(id));
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted()){
                //show Profile
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

}
