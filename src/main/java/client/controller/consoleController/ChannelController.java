package client.controller.consoleController;

import client.ClientSocket;
import client.FileHandler;
import model.exception.ResponseNotFoundException;
import model.message.FileMessage;
import model.message.Message;
import model.message.MessageType;
import model.message.TextMessage;
import model.request.Channel.*;
import model.response.Response;
import model.response.channel.GetChatHistoryRes;
import model.response.channel.GetPinnedMsgRes;
import model.server.Channel;
import model.server.ChannelType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

public class ChannelController extends InputController {

    private String channelName;
    private String serverId;
    private String userId;
    private String newName;

    public ChannelController(ClientSocket clientSocket) {
        super(clientSocket);
        this.channelName = null;
        this.serverId = null;
        this.userId = null;
    }

    /**
     * this method takes user id from client and
     * removes the user from the channel
     *
     * it is possible when user has the rule to remove someone
     * @author Arman sagharchi
     */
    public void removeUser(){


        System.out.println("enter server id : ");
        serverId = scanner.nextLine();
        System.out.println("enter channel name");
        channelName = scanner.nextLine();
        System.out.println("enter userId : ");
        userId = scanner.nextLine();

        //sends a request to the server
        try {
            clientSocket.send(new RemoveUserChannelReq(clientSocket.getId(), serverId,
                    channelName, userId));
            //takes the response from server
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * takes server id and channel name from client
     *
     * also takes the new name from client
     *
     * if user has the rule, server accepts the request
     * @author Arman sagharchi
     */
    public void renameChannel(){
        System.out.println("enter server id");
        serverId = scanner.nextLine();
        System.out.println("enter channel name");
        channelName = scanner.nextLine();
        System.out.println("enter new name : ");
        newName = scanner.nextLine();

        try{
            clientSocket.send(new RenameChannelReq(clientSocket.getId(), serverId,
                    channelName, newName));
            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println("\033[0;31mchannelName was successfully renamed to " + newName + "\033[0m");
            }
            else{
                System.out.println("\033[0;31mAccess denied to change the channel's name.");
                System.out.println(response.getMessage() + "\033[0m");
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * takes server id and channel name
     * and then sends a getting request and gets the response
     *
     * if req is accepted by user, a response in send which contains history of the chat
     * @return GetChatHistory response which contains linkedList of messages
     * @author Arman sagharchi
     */
    private GetChatHistoryRes getChatHis(){
        System.out.println("enter serverId : ");
        serverId = scanner.nextLine();
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();

        try{
            clientSocket.send(new GetChatHistoryReq(clientSocket.getId(), serverId, channelName));
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted()){
                return response instanceof GetChatHistoryRes ? (GetChatHistoryRes) response : null;
            }
            else{
                System.out.println("\033[0;31mAccess denied to get history chat.");
                System.out.println(response.getMessage() + "\033[0m");
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * first, takes the chat history response from the previous function
     * and then iterates on all the messages which is in the response
     * and prints messages
     *
     * and then saves messages received by the server to a file
     * @author Arman sagharchi
     */
    private void showChat(){

        GetChatHistoryRes GChatHisRes = getChatHis();

        if(GChatHisRes == null){
            return;
        }
        //prints all the messages existing in the linked list
        LinkedList<Message> messages = GChatHisRes.getMessages();

        for(Message message : messages){
            System.out.println(message);
        }

        //saving history of channel in file
        FileHandler.getFileHandler().saveMessage(messages);
    }

    /**
     * first it's optional to take chat's history...
     * if the user enters 1, chats will be shown
     *
     * then, sends message till the client enters the word '-0'
     * @author Arman sagharchi
     */
    public void ChannelChat(){

        System.out.println("want to get private chats ? ");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int choice = getOptionalInput(1,2);

        System.out.println("enter serverId : ");
        serverId = scanner.nextLine();
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();
        switch(choice){
            case 1 :
                //invokes the show chat method which shows all the chats
                showChat();
                break;
            case 2:
                break;
        }
        System.out.println("to stop chatting, enter the word '-0'. ");
        System.out.println("Enter 'FILE_MSG' to send file message :");
        //invokes the method which sends messages
        messageSender();



    }

    /**
     * a message sender, how does it work ?
     * has a while loop which continues till the client enters '-0'
     *
     * in order to send a file message, client should enter FILE_MSG
     *
     * @author Arman sagharchi
     */
    private void messageSender(){
        do{
            String content = scanner.nextLine();
            if(content.equals("-0")){
                return;
            }

            if(content.equals("FILE_MSG"))
            {
                sendFileMessage();
            }
            else
            {
                clientSocket.send(new TextMessage(null, clientSocket.getId(),serverId + "-" + channelName,
                        MessageType.CHANNEL, LocalDateTime.now(), content));
                try {
                    Response response = clientSocket.getReceiver().getResponse();
                    if (!response.isAccepted()) {
                        System.out.println("\033[0;31mAccess denied to send the message");
                    }

                    System.out.println(response.getMessage() + "\033[0m");
                }
                catch(ResponseNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        while(true);
    }

    /**
     * first takes server id and channel name
     * gets the pinned messages in a channel
     *
     * @return GetPinnedMessageResponse which contains all the pinned messages
     * @author Arman sagharchi
     */
    public GetPinnedMsgRes getPinnedMessage(){
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();
        System.out.println("enter server id : ");
        serverId = scanner.nextLine();

        try {
            clientSocket.send(new GetPinnedMsgReq(clientSocket.getId(), serverId, channelName));
            GetPinnedMsgRes response = (GetPinnedMsgRes)clientSocket.getReceiver().getResponse();

            if(response.isAccepted()){

                showPinnedMessages(response);

            }
            else
            {
                System.out.println("\033[0;31m" +response.getMessage() + "\033[0m");
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public void pinMessage()
    {
        System.out.println("enter server id");
        serverId = scanner.nextLine();
        System.out.println("enter channel name");
        channelName = scanner.nextLine();

        System.out.println();
        System.out.println("Enter messages id : ");
        String id = scanner.nextLine();

        try
        {
            clientSocket.send(new PinMessageReq(clientSocket.getId() , serverId , channelName , UUID.fromString(id)));

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

    public void unpinMessage()
    {
        System.out.println("enter server id");
        serverId = scanner.nextLine();
        System.out.println("enter channel name");
        channelName = scanner.nextLine();

        System.out.println();
        System.out.println("Enter messages id : ");
        String id = scanner.nextLine();

        try
        {
            clientSocket.send(new UnpinMessageReq(clientSocket.getId() , serverId , channelName , UUID.fromString(id)));

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
     * @param getPinnedMsgRes, which takes it from previous method
     * gets the linked list of pinned messages and then prints the messages
     *
     * @author Arman sagharchi
     */
    private void showPinnedMessages(GetPinnedMsgRes getPinnedMsgRes){
        //takes the linked list of all the pinned messages and prints them all
        LinkedList<Message> PMessages = getPinnedMsgRes.getPinnedMessages();

        System.out.println("Pinned messages : ");
        for(Message i : PMessages){
            System.out.println(i);
        }
        System.out.println("\n");
    }


    /**
     * creates a channel which need parameters channel name and server id
     *  which takes them from client
     *
     *  sends the creating request to the server and gets the response
     *  is the req is accepted, it means the channel is created
     *
     * @author Arman sagharchi
     */
    public void createChannel(){

        //takes server and channel name
        System.out.println("to be back, enter '-0'");
        System.out.println("enter channel name :");
        channelName = scanner.nextLine();
        if(channelName.equals("-0")) return;
        System.out.println("enter server id : ");
        serverId = scanner.nextLine();

        //asks fot type of channel
        System.out.println("type : \n");
        System.out.println("[1]-Text");
        System.out.println("[2]-Voice");

        ChannelType channelType;

        int choice = getOptionalInput(1, 2);

        if(choice == 1){
            //if channel type equals to text, creates a text channel
            channelType = ChannelType.TEXT;
        }
        else{
            //else, creates a voice channel
            channelType = ChannelType.VOICE;
        }

        if(serverId.equals("-0")) return;

        //sends the request
        clientSocket.send(new CreateChannelReq(clientSocket.getId(), serverId,
                channelName, channelType));

        try {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println("\033[0;31m" +response.getMessage() + "\033[0m");

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * first takes the channel name and server id
     * and then if such channel exists, server removes it
     *
     * @author Arman sagharchi
     */
    public void deleteChannel(){
        //info s needed to specify a channel
        System.out.println("to be back, enter '-0'");
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();

        if(channelName.equals("-0")) return;


        System.out.println("enter server id : ");
        serverId = scanner.nextLine();
        if(serverId.equals("-0")) return;

        //sends the delete request
        clientSocket.send(new DeleteChannelReq(clientSocket.getId(), serverId, channelName));
        try {
            //gets the response and then prints whether its accepted or not
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println("\033[0;31m" +response.getMessage() + "\033[0m");
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * adds a user to a channel
     * therefor, some information s including channel name, server id and user id needed to
     * add the user to the specified channel
     *
     * @author Arman sagharchi
     */
    public void addUserChannel(){
        System.out.println("enter server id : ");
        serverId = scanner.nextLine();
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();
        System.out.println("enter userId : ");
        userId = scanner.nextLine();

        try{
            //sends the adding request to the server
            clientSocket.send(new AddUserChannelReq(clientSocket.getId(), serverId,
                    channelName, userId));
            //takes the response and prints the message response
            Response response = clientSocket.getReceiver().getResponse();

            System.out.println("\033[0;31m" +response.getMessage() + "\033[0m");
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * sends a file message to the channel or private chat
     * first takes the file path and writes contents into the file specified
     *
     * @author mahdi kalhor
     */
    private void sendFileMessage()
    {
        System.out.println("enter files path :");

        String url = scanner.nextLine();

        //write content into file
        try
        {
            //takes a directory and reads all the bytes in that path,then writes in into the
            //file byte array

            byte[] file = Files.readAllBytes(Path.of(url));

            //sends File message to the server
            clientSocket.send(new FileMessage(null , clientSocket.getId() , serverId + "-" + channelName ,
                    MessageType.CHANNEL, LocalDateTime.now() , url.substring(url.lastIndexOf('/')) , file));

            try {
                //takes the response from server
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
