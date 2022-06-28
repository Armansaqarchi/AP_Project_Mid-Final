package client.controller.consoleController;

import client.ClientSocket;
import messenger.service.model.exception.ResponseNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.message.MessageType;
import messenger.service.model.message.TextMessage;
import messenger.service.model.request.priavteChat.GetPrivateChatHisReq;
import messenger.service.model.request.server.RenameServerReq;
import messenger.service.model.request.user.*;
import messenger.service.model.response.Response;
import messenger.service.model.response.privateChat.GetPrivateChatHisRes;
import messenger.service.model.response.user.GetFriendListRes;
import messenger.service.model.response.user.GetMyProfileRes;
import messenger.service.model.response.user.GetServersRes;
import messenger.service.model.response.user.GetUserProfileRes;
import messenger.service.model.user.ServerIDs;
import messenger.service.model.user.UserStatus;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class UserController extends InputController {

    private String userId;

    public UserController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    private GetFriendListRes getFriendList() {
        clientSocket.send(new GetFriendListReq(clientSocket.getId()));

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

        GetFriendListRes response = getFriendList();

        if(response == null){
            System.err.println("No valid response was received");
        }
        else if(!response.isAccepted()){
            System.err.println(response.getMessage());
        }
        else{
            HashMap<String, UserStatus> list = response.getFriendList();

            int counter = 1;
            for(String string : list.keySet()){
                System.out.println("[" + counter + "] " + string + " : " + list.get(string));
                counter++;
            }
        }
    }

    public GetUserProfileRes getProfile(String id) {
        try {
            clientSocket.send(new GetUserProfileReq(clientSocket.getId(), id));
            return (GetUserProfileRes)clientSocket.getReceiver().getResponse();
        }
        catch(ResponseNotFoundException e){
            System.err.println(e.getMessage());
        }

        return null;
    }


    private void showUserProfile(String id){
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
            clientSocket.send(new BlockUserReq(clientSocket.getId(), id));
            Response response = clientSocket.getReceiver().getResponse();
            if(!response.isAccepted()){
                System.err.println(response.getMessage());
            }
            else{
                System.out.println("User successfully blocked");
            }
        }
        catch(ResponseNotFoundException e){
            System.err.println(e.getMessage());
        }

    }

    public void addFriend(String id) {
        try {
            clientSocket.send(new FriendReq(clientSocket.getId(), UUID.randomUUID(), id));
            Response response = clientSocket.getReceiver().getResponse();

            if(!response.isAccepted()){
                System.err.println(response.getMessage());
            }
            else{
                System.out.println("User successfully added");
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void showUserProfile()
    {
        System.out.println("Enter user's id :");

        userId = scanner.nextLine();

        showUserProfile(userId);
    }

    public void showMyProfile() {
        try {
            clientSocket.send(new GetMyProfileReq(clientSocket.getId()));

            GetMyProfileRes response = (GetMyProfileRes) clientSocket.getReceiver().getResponse();

            if (response.isAccepted()) {
                System.out.println(response.getMessage());
                System.out.println(response);

            } else {
                System.out.println(response.getMessage());
            }
        } catch (ResponseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void privateChat(String id){
        showPrivateChatHis(getPrivateChatHis(id));

        String content;

        try {
            do {
                content = scanner.nextLine();
                if (content.equals("-1")) {
                    //return to the previous menu
                }
                clientSocket.send(new TextMessage(null, clientSocket.getId(), id,MessageType.PRIVATE_CHAT,
                        LocalDateTime.now(), content));
                Response response = clientSocket.getReceiver().getResponse();
                if(!response.isAccepted()){
                    System.out.println(response.getMessage());
                }
            }
            while (true);
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    public void answerFriendRequest()
    {
        System.out.println("enter id of friendReq : ");
        String friendReqId = scanner.nextLine();
        System.out.println("Accept ? ");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int choice = getOptionalInput(1, 2);

        boolean isAccepted = false;

        if(choice == 1){
            isAccepted = true;
        }

        try{
            clientSocket.send(new AnswerFriendReq(clientSocket.getId(), UUID.fromString(friendReqId),
                    isAccepted));
            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println("successfully done.");
            }
            else{
                System.out.println("Access denied to modify changes");
                System.out.println(response.getMessage());
            }

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    private void getServers()
    {
        try{
            clientSocket.send(new GetServersReq(clientSocket.getId()));

            GetServersRes response = (GetServersRes)clientSocket.getReceiver().getResponse();

            if(response.isAccepted())
            {
                System.out.println(response.getMessage());

                //print servers
                showServers(response.getServers());
            }
            else
            {
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private GetPrivateChatHisRes getPrivateChatHis(String id){
        try{
            clientSocket.send(new GetPrivateChatHisReq(clientSocket.getId(), id));
            Response response = clientSocket.getReceiver().getResponse();
            if(response instanceof GetPrivateChatHisRes){
                return (GetPrivateChatHisRes) response;
            }
        }
        catch(ResponseNotFoundException e){
            System.err.println(e.getMessage());
        }

        return null;
    }


    private void showPrivateChatHis(GetPrivateChatHisRes PChatHisRes){
        if(PChatHisRes == null){
            System.err.println("No Valid response was received from server");
        }
        else if(!PChatHisRes.isAccepted()){
            System.err.println(PChatHisRes.getMessage());
        }
        else{
            LinkedList<Message> chatMessages = PChatHisRes.getMessages();

            for(Message i : chatMessages){
                i.showMessage();
            }


            System.out.println("to be back, press '-1'.");
        }


    }

    private void showServers(LinkedList<ServerIDs> serverIDs)
    {
        for(ServerIDs serverId : serverIDs)
        {
            System.out.println("server :" + serverId.getId() + " channels : ");

            for(String channelName : serverId.getChannels())
            {
                System.out.println(channelName + " \n");
            }
        }

        System.out.println();
    }


}
