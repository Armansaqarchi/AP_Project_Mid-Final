package client.controller.consoleController;

import client.ClientSocket;
import client.FileHandler;
import model.exception.ResponseNotFoundException;
import model.message.Message;
import model.message.MessageType;
import model.message.TextMessage;
import model.request.GetFileMsgReq;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.request.user.*;
import model.response.GetFileMsgRes;
import model.response.Response;
import model.response.privateChat.GetPrivateChatHisRes;
import model.response.user.*;
import model.user.UserStatus;


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
           System.out.println(e.getMessage());
       }

        return null;

    }

    public void showFriendList(){

        GetFriendListRes response = getFriendList();

        if(response == null){
            System.out.println("\033[0;31mNo valid response was received\033[0m");

        }
        else if(!response.isAccepted()){
            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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
            System.out.println(e.getMessage());
        }

        return null;
    }


    private void showUserProfile(String id){
        GetUserProfileRes response = getProfile(id);

        if(response == null){
            System.out.println("\033[0;31mNo valid response was received\033[0m");

        }
        else if(!response.isAccepted()){
            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
        }
        else {
            System.out.println("Id : " +  response.getId());
            System.out.println("Name : " + response.getName());
            System.out.println("Status : " +  response.getUserStatus());

            String profileImage = response.getProfileImage()
                    != null ? "has profile" : "does not have profile";
            System.out.println("Profile image : " + profileImage);
        }
    }

    public void blockUser(){

        System.out.println("enter id of user whom you want to block");
        System.out.println("to back, enter '-0'");
        userId = scanner.nextLine();
        if(userId.equals("-0")){
            return;
        }

        try {
            clientSocket.send(new BlockUserReq(clientSocket.getId(), userId));
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    public void addFriend() {

        System.out.println("enter id of user whom you want to add");
        System.out.println("to be back, press '-0'");
        userId = scanner.nextLine();
        if(userId.equals("-0")){
            return;
        }

        try {
            clientSocket.send(new FriendReq(clientSocket.getId(), UUID.randomUUID(), userId));
            Response response = clientSocket.getReceiver().getResponse();


            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void showUserProfile()
    {
        System.out.println("Enter user's id :");
        System.out.println("to be back, enter '-0'");
        userId = scanner.nextLine();

        if(userId.equals("-0")) return;

        showUserProfile(userId);
    }


    public void privateChat() {


        showPrivateChatHis();

        if (userId.equals("-0")) return;

        System.out.println("to be back, press '-0'.");

        String content;


        do {
            try {
                content = scanner.nextLine();
                if (content.equals("-0")) {
                    return;
                }
                clientSocket.send(new TextMessage(null, clientSocket.getId(), userId, MessageType.PRIVATE_CHAT,
                        LocalDateTime.now(), content));
                Response response = clientSocket.getReceiver().getResponse();

                    System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            } catch (ResponseNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        while(true);

    }

    public void answerFriendRequest()
    {

    }

    private GetFriendReqListRes getFriendReqList(){
        clientSocket.send(new GetFriendReqList(clientSocket.getId()));
        try {
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            if(response.isAccepted()){
                if(response instanceof GetFriendReqListRes){
                    return (GetFriendReqListRes) response;
                }
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void showFriendReqList(){
        GetFriendReqListRes getFriendReqList = getFriendReqList();

        if(getFriendReqList == null){
            return;
        }

        for(UUID i : getFriendReqList.getFriendRequests()){
            System.out.println(i);
        }

    }

    private GetBlockedUsersRes getBlockedUsers(){
        clientSocket.send(new GetBlockedUsersReq(clientSocket.getId()));
        try{
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            if(response.isAccepted()){
                if(response instanceof GetBlockedUsersRes){
                    return (GetBlockedUsersRes) response;
                }
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void showBlockedUsers(){
        GetBlockedUsersRes getBlockedUsersRes = getBlockedUsers();

        if(getBlockedUsersRes == null){
            return;
        }

        for(String i : getBlockedUsersRes.getBlockedUsers()){
            System.out.println(i);
        }
    }


    private GetPrivateChatHisRes getPrivateChatHis(){

        System.out.println("enter friend id : ");
        System.out.println("to be back, enter '-0'");
        userId = scanner.nextLine();
        if(userId.equals("-0")){
            return null;
        }

        try{

            clientSocket.send(new GetPrivateChatHisReq(clientSocket.getId(), userId));

            Response response = clientSocket.getReceiver().getResponse();

            if(response instanceof GetPrivateChatHisRes){
                return (GetPrivateChatHisRes) response;
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private GetPrivateChatsRes getChats(){
        clientSocket.send(new GetPrivateChatsReq(clientSocket.getId()));
        try{
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            if(response.isAccepted()){
                if(response instanceof GetPrivateChatsRes){
                    return (GetPrivateChatsRes) response;
                }
            }
        }catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }


    public void showChats(){

        GetPrivateChatsRes getPrivateChatsRes = getChats();

        if(getPrivateChatsRes == null){
            return;
        }

        for(String i : getPrivateChatsRes.getPrivateChats()){
            System.out.println(i);
        }
    }


    public void showPrivateChatHis(){

        GetPrivateChatHisRes PChatHisRes = getPrivateChatHis();

        if(PChatHisRes == null){
            return;
        }

        System.out.println("\033[0;31m" + PChatHisRes.getMessage() + "\033[0m");

        if(PChatHisRes.isAccepted())
        {
            LinkedList<Message> chatMessages = PChatHisRes.getMessages();

            System.out.println(chatMessages.size());
            for(Message i : chatMessages){
                System.out.println(i);
            }

            //saving chat history in file
            FileHandler.getFileHandler().saveMessage(chatMessages);
        }
    }

    public void getFileMsg()
    {
        System.out.println("Enter messages id : ");
        String id = scanner.nextLine();

        try
        {
            clientSocket.send(new GetFileMsgReq(clientSocket.getId() , UUID.fromString(id)));

            GetFileMsgRes response = (GetFileMsgRes) clientSocket.getReceiver().getResponse();

            System.out.println(response.getMessage());

            FileHandler.getFileHandler().saveFile(response);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\033[0;31mInvalid message id!\033[0m");
        } catch (ResponseNotFoundException e)
        {
            System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
        }
    }
}
