package controller.consoleController;

import client.ClientSocket;
import client.Receiver;
import messenger.service.model.exception.ResponseNotFoundException;
import messenger.service.model.request.user.BlockUserReq;
import messenger.service.model.request.user.GetFriendListReq;
import messenger.service.model.request.user.GetUserProfileReq;
import messenger.service.model.request.user.UserRequestType;
import messenger.service.model.response.Response;
import messenger.service.model.response.user.GetFriendListRes;
import messenger.service.model.response.user.GetUserProfileRes;
import messenger.service.model.user.UserStatus;


import java.util.HashMap;
import java.util.LinkedList;

public class FriendController extends InputController {

    public FriendController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    private GetFriendListRes getFriendList() {
        clientSocket.send(new GetFriendListReq(UserRequestType.GET_FRIEND_LIST));

       try {
           Response response = clientSocket.getReceiver().getResponse();

           if (response instanceof GetFriendListRes) {
               return ((GetFriendListRes) response);
           }
       }
       catch(ResponseNotFoundException e){
           System.err.println(e.getMessage());
       }

        return null;

    }

    public void showFriendList(){

        Response response = getFriendList();

        if(response == null){
            System.err.println("No valid response was received");
        }
        else if(!response.isAccepted()){
            System.err.println(response.getMessage());
        }
        else{
            HashMap<String, UserStatus> list = ((GetFriendListRes)response).getFriendsList();

            int counter = 1;
            for(String string : list.keySet()){
                System.out.println("[" + counter + "] " + string + " : " + list.get(string));
                counter++;
            }
        }
    }

    public GetUserProfileRes getProfile(String id) {
        try {
            clientSocket.send(new GetUserProfileReq(UserRequestType.GET_USER_PROFILE, id));
            return (GetUserProfileRes)clientSocket.getReceiver().getResponse();
        }
        catch(ResponseNotFoundException e){
            System.err.println(e.getMessage());
        }

        return null;
    }


    public void showProfile(String id){
        GetUserProfileRes response = getProfile(id);

        if(response == null){
            System.err.println("No valid response was received");
        }
        else if(!response.isAccepted()){
            System.err.println(response.getMessage());
        }
        else {
            System.out.println("Id : " +  response.getId());
            System.out.println("Name : " + response.getName());
            System.out.println("Status : " +  response.getUserStatus());

            String profileImage = ((GetUserProfileRes) response).getProfileImage()
                    != null ? "has profile" : "does not have profile";
            System.out.println("Profile image : " + profileImage);
        }
    }

    public void blockUser(String id){
        try {
            clientSocket.send(new BlockUserReq(UserRequestType.BLOCK_USER, id));
            Response response = clientSocket.getReceiver().getResponse();
            if(!response.isAccepted()){
                System.err.println(response.getMessage());
            }
            else{
                System.out.println("user successfully blocked");
            }
        }
        catch(ResponseNotFoundException e){
            System.err.println(e.getMessage());
        }

    }
}
