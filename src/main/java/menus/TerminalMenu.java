package menus;

import client.ClientSocket;
import client.controller.consoleController.Controllers;
import client.controller.consoleController.InputController;

public class TerminalMenu {

    private static TerminalMenu terminalMenu;
    private Controllers controllers;

    private TerminalMenu(ClientSocket clientSocket) {
        controllers = Controllers.getControllers(clientSocket);
    }

    public void mainMenu(){
            System.out.println("[1]-Friends");
            System.out.println("[2]-Servers");
            System.out.println("[3]-Profile");
            System.out.println("[4]-Back");
            int choice = InputController.getOptionalInput(1, 4);

            switch (choice) {
                case 1 -> friendsMenu();
                case 2 -> serversMenu();
                case 3 -> profileMenu();
            }


    }

    private void friendsMenu(){
        System.out.println("[1]-Chat");
        System.out.println("[2]-Friends List");
        System.out.println("[3]-Add Friend");
        System.out.println("[4]-Search User");
        System.out.println("[5]-Block user");
        System.out.println("[6]-Answer Friend Request");
        System.out.println("[7]-Back");

        int choice = InputController.getOptionalInput(1, 8);
        switch(choice){
            case 1 -> controllers.getUserController().privateChat();
            case 2 -> controllers.getUserController().showFriendList();
            case 3 -> controllers.getUserController().addFriend();
            case 4 -> controllers.getUserController().showUserProfile();
            case 5 -> controllers.getUserController().blockUser();
            case 6 -> controllers.getUserController().answerFriendRequest();
        }
    }

    private void serversMenu(){
        System.out.println("[1]-Channels");
        System.out.println("[2]-Edit Server");
        System.out.println("[3]-Back");

        int choice = InputController.getOptionalInput(1, 3);
        switch(choice){
            case 1 -> channelMenu();
            case 2 -> editServerMenu();
        }
    }

    private void profileMenu(){
        System.out.println("[1]-Show Profile");
        System.out.println("[2]-Change Profile");
        System.out.println("[3]-Back");

        int choice = InputController.getOptionalInput(1, 3);
        switch(choice){
            case 1 -> controllers.getProfileController().
            case 2 ->
            case 3 ->
        }
    }

    private void chatMenu(){
        System.out.println("[1]-Chat");
        System.out.println("[2]-Get History Chat");
        System.out.println("[3]-All the Chats");
        System.out.println("[4]-Friend Requests list");
        System.out.println("[5]-Blocked Users");
        System.out.println("[6]-Back");

        int choice = InputController.getOptionalInput(1, 6);
        switch(choice){
            case 1 ->
            case 2 ->
            case 3 ->
            case 4 ->
            case 5 ->
            case 6 ->
            case 7 ->
            case 8 ->
        }
    }

    private void channelMenu(){
        System.out.println("[1]-Chat");
        System.out.println("[2]-Chats History");
        System.out.println("[3]-Create Channel");
        System.out.println("[4]-Delete Channel");
        System.out.println("[5]-Rename Channel");
        System.out.println("[6]-Add User");
        System.out.println("[7]-Remove User");
        System.out.println("[8]-Back");

        int choice = InputController.getOptionalInput(1, 8);
        switch(choice){
            case 1 ->
            case 2 ->
            case 3 ->
            case 4 ->
            case 5 ->
            case 6 ->
            case 7 ->
            case 8 ->
        }

    }

    private void editServerMenu(){
        System.out.println("[1]-Server Info");
        System.out.println("[2]-Create Server");
        System.out.println("[3]-Delete Server");
        System.out.println("[4]-Rename Server");
        System.out.println("[5]-Add User");
        System.out.println("[6]-Remove User");
        System.out.println("[7]-Add Rule");
        System.out.println("[8]-Users Status");
        System.out.println("[9]-Set Server Image");
        System.out.println("[10]-Back");
        int choice = InputController.getOptionalInput(1, 8);
        switch(choice){
            case 1 ->
            case 2 ->
            case 3 ->
            case 4 ->
            case 5 ->
            case 6 ->
            case 7 ->
            case 8 ->
            case 9 ->
            case 10 ->
        }
    }

}
