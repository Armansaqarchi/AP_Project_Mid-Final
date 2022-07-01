package client.controller.consoleController;

import client.ClientSocket;
import client.FileHandler;
import model.exception.ResponseNotFoundException;
import model.message.FileMessage;
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


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class UserController extends InputController {

    private String userId;

    public UserController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    /**
     * gets all the friends
     * no need to take anything from client
     * @return getFriendList response which contains a linked list of all the friends
     */
    private GetFriendListRes getFriendList() {
        clientSocket.send(new GetFriendListReq(clientSocket.getId()));

       try {
           //takes the response from client
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

    /**
     * shows all the friends list
     * first takes the friends list response from previous method
     * and then takes the linked list from the response and prints all the friends details
     * @author Arman sagharchi
     */
    public void showFriendList(){

        GetFriendListRes response = getFriendList();

        //if the response is null
        if(response == null){
            System.out.println("\033[0;31mNo valid response was received\033[0m");

        }
        //if server did not accept the request
        else if(!response.isAccepted()){
            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
        }
        //if its accepted, print all the friends
        else{
            HashMap<String, UserStatus> list = response.getFriendList();

            int counter = 1;
            for(String string : list.keySet()){
                System.out.println("[" + counter + "] " + string + " : " + list.get(string));
                counter++;
            }
        }
    }

    /**
     * takes user id and prints public infos about the user
     * @author Arman sagharchi
     */
    public GetUserProfileRes getProfile(String id) {
        try {
            //sends the related req
            clientSocket.send(new GetUserProfileReq(clientSocket.getId(), id));
            return (GetUserProfileRes)clientSocket.getReceiver().getResponse();
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * takes the id from client and invokes the previous method to get the profile
     * @param id, the id which is taken from client
     * prints information about the user profile
     * @author Arman sagharchi
     */
    private void showUserProfile(String id){
        GetUserProfileRes response = getProfile(id);

        if(response == null){
            System.out.println("\033[0;31mNo valid response was received\033[0m");

        }
        else if(!response.isAccepted()){
            //if the req is now accepted, print the message
            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
        }
        else {
            //if its accepted, print the infos
            System.out.println("Id : " +  response.getId());
            System.out.println("Name : " + response.getName());
            System.out.println("Status : " +  response.getUserStatus());

            String profileImage = response.getProfileImage()
                    != null ? "has profile" : "does not have profile";
            System.out.println("Profile image : " + profileImage);
        }
    }

    /**
     * takes the id of user from client and blocks the user
     * @author Arman sagharchi
     */
    public void blockUser(){

        //takes the user id from client
        System.out.println("enter id of user whom you want to block");
        System.out.println("to back, enter '-0'");
        userId = scanner.nextLine();
        if(userId.equals("-0")){
            return;
        }

        try {
            //sends the related req to the client
            clientSocket.send(new BlockUserReq(clientSocket.getId(), userId));
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * takes the user id from client and checks if the user exists.
     * if user exists, sends a friend request to the user
     * @author Arman sagharchi
     */
    public void addFriend() {

        //takes id from client
        System.out.println("enter id of user whom you want to add");
        System.out.println("to be back, press '-0'");
        userId = scanner.nextLine();
        if(userId.equals("-0")){
            return;
        }

        try {
            //sends the related request to the clinet
            clientSocket.send(new FriendReq(clientSocket.getId(), UUID.randomUUID(), userId));
            Response response = clientSocket.getReceiver().getResponse();


            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
    * takes the id from client and shows user profile
    * @author Arman sagharchi
     */
    public void showUserProfile()
    {
        System.out.println("Enter user's id :");
        System.out.println("to be back, enter '-0'");
        userId = scanner.nextLine();

        if(userId.equals("-0")) return;

        showUserProfile(userId);
    }


    /**
     * first shows the history chat between to user and then lets the user to chat
     * it ends until the user enter  '-0'
     * to send a file message, client should enter FILE_MSG
     * @author Arman sagharchi
     */
    public void privateChat() {


        //shows the history chat
        showPrivateChatHis();

        if (userId.equals("-0")) return;

        System.out.println("to be back, press '-0'.");
        System.out.println("Enter 'FILE_MSG' to send file message :");

        //content of message
        String content;


        do {

            content = scanner.nextLine();

            if (content.equals("-0")) {
                return;
            }

            if(content.equals("FILE_MSG"))
            {
                //if client wants to send file message, it invokes the file sender message
                sendFileMessage();
            }
            else
            {
                //sends the related req
                clientSocket.send(new TextMessage(null, clientSocket.getId(), userId, MessageType.PRIVATE_CHAT,
                        LocalDateTime.now(), content));

                try {
                    //takes the response from server
                    Response response = clientSocket.getReceiver().getResponse();

                    System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

                } catch (ResponseNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        while(true);

    }

    /**
     *this method takes the id from client and also ask the client if it wants to accept it ?
     * takes an input from client
     * then sends the answer friend req response
     * @author Arman sagharchi
     */
    public void answerFriendReq()
    {
        boolean isAccepted;

        //takes the idn from user
        System.out.println("enter '-0' in order to exit");
        System.out.println("Enter friend req id : ");
        String id = scanner.nextLine();
        if(id.equals("-0")){
            return;
        }

        System.out.println("[1]-Accept");
        System.out.println("[2]-Ignore");

        int choice = getOptionalInput(1, 2);

        if(choice == 1){
            isAccepted = true;
        }
        else{
            isAccepted = false;
        }

        try
        {
            //sends the req to the server
            clientSocket.send(new AnswerFriendReq(clientSocket.getId(), UUID.fromString(id), isAccepted));

            Response response = clientSocket.getReceiver().getResponse();

            System.out.println(response.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\033[0;31mInvalid message id!\033[0m");
        } catch (ResponseNotFoundException e)
        {
            System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
        }
    }

    /**
     * this method takes no parameter from user
     * @return response for friend req lists
     * @author Arman sagharchi
     */
    private GetFriendReqListRes getFriendReqList(){
        //sends the related req
        clientSocket.send(new GetFriendReqList(clientSocket.getId()));
        try {
            //takes the response from server
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

    /**
     * takes response of friend req list from server
     * and then takes the linked list of all the friend reqs
     * and shows all the friend lists
     * @author Arman sagharchi
     */
    public void showFriendReqList(){
        GetFriendReqListRes getFriendReqList = getFriendReqList();

        if(getFriendReqList == null){
            return;
        }

        //iterates and prints all the friend reqs
        for(UUID i : getFriendReqList.getFriendRequests().keySet()){
            System.out.println("sender id : " + getFriendReqList.getFriendRequests().get(i)
                    + "uuid : " + i);
        }

    }

    /**
     * takes no parameter from client
     * sends related request about getting blocked list from server
     * takes the response from server
     * @return response of blocked list from server
     * @autho Arman sagharchi
     */
    private GetBlockedUsersRes getBlockedUsers(){
        clientSocket.send(new GetBlockedUsersReq(clientSocket.getId()));
        try{
            //sends the req to the server
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

    /**
     * gets the response og blocked list from the previous method
     * then, takes the linked list containing all the blocked users
     * and shows the users
     * @author Arman sagharchi
     */
    public void showBlockedUsers(){
        GetBlockedUsersRes getBlockedUsersRes = getBlockedUsers();

        if(getBlockedUsersRes == null){
            return;
        }

        //prints all the users
        for(String i : getBlockedUsersRes.getBlockedUsers()){
            System.out.println(i);
        }
    }

    /**
     * takes user id from client and then, sends the getting private chat
     * history req to the server
     * @return the response containing all the blocked users
     * @author Arman sagharchi
     */
    private GetPrivateChatHisRes getPrivateChatHis(){

        System.out.println("enter friend id : ");
        System.out.println("to be back, enter '-0'");
        userId = scanner.nextLine();
        if(userId.equals("-0")){
            return null;
        }

        try{
            //sends the related req
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

    /**
     * this method takes all the private chats for a user
     * @return a response containing list of al the private chats
     * @author Arman sagharchi
     */
    private GetPrivateChatsRes getChats(){
        clientSocket.send(new GetPrivateChatsReq(clientSocket.getId()));
        try{
            //sends the req
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            if(response.isAccepted()){
                //if it's accepted by server
                if(response instanceof GetPrivateChatsRes){
                    return (GetPrivateChatsRes) response;
                }
            }
        }catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * takes the response from previous method and then,
     * shows all the chats in a well-formed format
     * @author Arman sagharchi
     */
    public void showChats(){

        GetPrivateChatsRes getPrivateChatsRes = getChats();

        if(getPrivateChatsRes == null){
            return;
        }

        for(String i : getPrivateChatsRes.getPrivateChats()){
            System.out.println(i);
        }
    }

    /**
     * takes the response of private chat history from previous method and then,
     * prints all the messages for private chat history
     * @author Arman sagharchi
     */
    public void showPrivateChatHis(){

        GetPrivateChatHisRes PChatHisRes = getPrivateChatHis();

        if(PChatHisRes == null){
            return;
        }

        System.out.println("\033[0;31m" + PChatHisRes.getMessage() + "\033[0m");

        if(PChatHisRes.isAccepted())
        {
            //if server accepted and sent all the chats, show the chats
            LinkedList<Message> chatMessages = PChatHisRes.getMessages();

            System.out.println(chatMessages.size());
            for(Message i : chatMessages){
                System.out.println(i);
            }

            //saving chat history in file
            FileHandler.getFileHandler().saveMessage(chatMessages);
        }
    }

    /**
     * this method is used to take the file message
     * and save it to the directory specified by the user
     * @author mahdi kalhor
     */
    public void getFileMsg()
    {
        System.out.println("Enter messages id : ");
        String id = scanner.nextLine();

        try
        {
            //sends the req
            clientSocket.send(new GetFileMsgReq(clientSocket.getId() , UUID.fromString(id)));

            GetFileMsgRes response = (GetFileMsgRes) clientSocket.getReceiver().getResponse();

            System.out.println(response.getMessage());

            //saves it to the path specified by the client
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

    /**
     * sends the file message
     * first takes the file name of file message
     * and then, writes the bytes to the byte array
     * and after all, sends the message to the chat
     * @author mahdi kalhor
     */
    private void sendFileMessage()
    {
        System.out.println("enter files path :");

        String url = scanner.nextLine();

        //write content into file
        try
        {
            byte[] file = Files.readAllBytes(Path.of(url));

            //sends the req to the server
            clientSocket.send(new FileMessage(null , clientSocket.getId() , userId ,
                    MessageType.PRIVATE_CHAT, LocalDateTime.now() , url.substring(url.lastIndexOf('/')) , file));

            try {
                //takes the response specifies it's sent or not
                Response response = clientSocket.getReceiver().getResponse();

                System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

            } catch (ResponseNotFoundException e) {
                System.out.println(e.getMessage());
            }

        }
        catch (IOException e)
        {
            System.out.println("\033[0;31mfailed to open the file.\033[0m");
        }
    }
}
