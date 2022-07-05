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

    //has essential info s about profile
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private UserStatus userStatus;

    private byte[] profileImage;

    /**
     * @param clientSocket, to interact with server, a client socket is needed
     * @author Arman sagharchi
     */
    public ProfileController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    /**
     * this method updates the user profile
     * @author Arman sagharchi
     */
    public void updateProfile(){

        //at first has its field all assigned to null
        id = null;
        name = null;
        password = null;
        email = null;
        phoneNumber = null;
        profileImage = null;
        userStatus = null;

        //the boolean which decides where to exist this section
        boolean isRunning = true;

        while(isRunning){

            //menu
            System.out.println("[1] change id" + "            current : " + id);
            System.out.println("[2] change name" + "          current : " + name);
            System.out.println("[3] change email" + "         current : " + email);
            System.out.println("[4] change password" + "      current : " + password);
            System.out.println("[5] change phone number" + "  current : " + phoneNumber);
            System.out.println("[6] change user status" + "   current : " + userStatus);
            System.out.println("[7] submit");

            //gets an input from user and invokes the following methods based by selected option
            switch(getOptionalInput(1, 8)){
                case 1 -> changeId();
                case 2 -> changeName();
                case 3 -> changeEmail();
                case 4 -> changePassword();
                case 5 -> changePhoneNumber();
                case 6 -> changeUserStatus();
                case 7 -> isRunning = false;
            }

            //invokes the method which sends the related request
            newProfileSender();

        }

    }

    /**
     * sends a new profile request and gets the response from server
     * @author Arman sagharchi
     */
    private void newProfileSender(){
        //sends the request
        clientSocket.send(new SetMyProfileReq(clientSocket.getId(), id, name,
                password, email, phoneNumber, profileImage, userStatus));
        try {
            //takes the request
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println("\033[0;31m" +response.getMessage() + "\033[0m");
            if(response.isAccepted()){
                clientSocket.setId(id);
            }

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * sends the showMyProf req and get its related response which has the profile needs
     * @author Arman sagharchi
     */
    public void showMyProfile()
    {
        try
        {
            clientSocket.send(new GetMyProfileReq(clientSocket.getId()));

            GetMyProfileRes response = (GetMyProfileRes)clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" +response.getMessage() + "\033[0m");

            if(response.isAccepted())
            {
                System.out.println(response);

            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * simply changes the id
     * @author Arman sagharchi
     */
    private void changeId(){
        System.out.println("enter new id : ");
        id = scanner.nextLine();
    }

    private void changeName(){
        System.out.println("enter new name : ");
        String newName = scanner.nextLine();
        try{
            InfoVerifier.checkUserValidity(newName);
            this.name = newName;
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
                this.password = newPass;
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("\033[0;31mthere is mismatch between password and confirm password, try again.\033[0m");
        }
    }

    /**
     * simply changes the email
     * @author Arman sagharchi
     */
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

    /**
     * shows all the possible statuses to change
     * takes the selected option from client and sets the new status
     * @author Arman sagharchi
     */
    private void changeUserStatus(){

        //options possible to set the status to
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

    /**
     * simply changes the phone number
     * also checks the validity of phone number
     * @author Arman sagharchi
     */
    private void changePhoneNumber(){
        //takes new phone number
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
