package menus;

import ch.qos.logback.core.net.server.Client;
import client.ClientSocket;
import client.controller.consoleController.Controllers;
import client.controller.consoleController.InputController;

public class TerminalMenu {

    private static TerminalMenu terminalMenu;
    private Controllers controllers;

    private TerminalMenu(ClientSocket clientSocket) {
        controllers = Controllers.getControllers(clientSocket);
    }

    public static TerminalMenu getTerminalMenu(ClientSocket clientSocket){
        if(terminalMenu == null){
            return new TerminalMenu(clientSocket);
        }

        return terminalMenu;
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
            case 1 -> controllers.getProfileController().showMyProfile();
            case 2 -> controllers.getProfileController().updateProfile();
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
            case 1 -> controllers.getUserController().privateChat();
            case 2 -> controllers.getUserController().getPrivateChatHis();
            case 3 -> controllers.getUserController().getChats();
            case 4 -> controllers.getUserController().showFriendReqList();
            case 5 -> controllers.getUserController().showBlockedUsers();
        }
    }

    private void channelMenu(){
        System.out.println("[1]-Chat");
        System.out.println("[2]-Create Channel");
        System.out.println("[3]-Delete Channel");
        System.out.println("[4]-Rename Channel");
        System.out.println("[5]-Add User");
        System.out.println("[6]-Remove User");
        System.out.println("[7]-Back");

        int choice = InputController.getOptionalInput(1, 8);
        switch(choice){
            case 1 -> controllers.getChannelController().ChannelChat();
            case 2 -> controllers.getChannelController().createChannel();
            case 3 -> controllers.getChannelController().deleteChannel();
            case 4 -> controllers.getChannelController().renameChannel();
            case 5 -> controllers.getChannelController().addUserChannel();
            case 6 -> controllers.getChannelController().removeUser();
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
            case 1 -> controllers.getServerController().getServerInfo();
            case 2 -> controllers.getServerController().creatServer();
            case 3 -> controllers.getServerController().deleteServer();
            case 4 -> controllers.getServerController().renameServer();
            case 5 -> controllers.getServerController().addUser();
            case 6 -> controllers.getServerController().removeUser();
            case 7 -> controllers.getServerController().addRule();
            case 8 -> controllers.getServerController().showUsersStatus();
            case 9 -> controllers.getServerController().setServerImage();
        }
    }

}
