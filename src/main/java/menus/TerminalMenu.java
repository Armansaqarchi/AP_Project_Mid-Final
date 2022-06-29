package menus;

public class TerminalMenu {

    public void mainMenu(){
        System.out.println("[1]-Friends");
        System.out.println("[2]-Servers");
        System.out.println("[3]-Profile");
        System.out.println("[4]-Back");
    }

    private void FriendsMenu(){
        System.out.println("[1]-Chat");
        System.out.println("[2]-Friends List");
        System.out.println("[3]-Add Friend");
        System.out.println("[4]-Search User");
        System.out.println("[5]-Block user");
        System.out.println("[6]-Friend Request");
        System.out.println("[7]-Answer Friend Request");
        System.out.println("[8]-Back");
    }

    private void serversMenu(){
        System.out.println("[1]-Channels");
        System.out.println("[2]-Edit Server");
        System.out.println("[3]-Back");
    }

    private void ProfileMenu(){
        System.out.println("[1]-Show Profile");
        System.out.println("[2]-Change Profile");
        System.out.println("[3]-Back");
    }

    private void chatMenu(){
        System.out.println("[1]-Chat");
        System.out.println("[2]-Get History Chat");
        System.out.println("[3]-All the Chats");
        System.out.println("[4]-Friend Requests list");
        System.out.println("[5]-Blocked Users");
        System.out.println("[6]-Back");
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
    }

}
