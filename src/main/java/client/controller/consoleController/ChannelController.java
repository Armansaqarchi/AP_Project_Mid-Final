package client.controller.consoleController;

import client.ClientSocket;
import model.exception.ResponseNotFoundException;
import model.message.Message;
import model.message.MessageType;
import model.message.TextMessage;
import model.request.Channel.*;
import model.response.Response;
import model.response.channel.GetChatHistoryRes;
import model.response.channel.GetPinnedMsgRes;
import model.server.Channel;
import model.server.ChannelType;

import java.time.LocalDateTime;
import java.util.LinkedList;

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

    public void removeUser(){


        System.out.println("enter server id : ");
        serverId = scanner.nextLine();
        System.out.println("enter channel name");
        channelName = scanner.nextLine();
        System.out.println("enter userId : ");
        userId = scanner.nextLine();

        try {
            clientSocket.send(new RemoveUserChannelReq(clientSocket.getId(), serverId,
                    channelName, userId));
            Response response = clientSocket.getReceiver().getResponse();
            if(!response.isAccepted()){
                System.err.println("Access denied to remove from channel.");
                System.err.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

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
                System.out.println("channelName was successfully renamed to " + newName);
            }
            else{
                System.out.println("Access denied to change the channel's name.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

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
                System.out.println("Access denied to get history chat.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void showChat(){

        GetChatHistoryRes GChatHisRes = getChatHis();

        if(GChatHisRes == null){
            return;
        }
        LinkedList<Message> messages = GChatHisRes.getMessages();

        for(Message message : messages){
            System.out.println(message);
        }
    }


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
                showChat();
                break;
            case 2:
                break;
        }
        System.out.println("to stop chatting, enter the word '-0'. ");
        messageSender();

        //return to the previous menu

    }

    private void messageSender(){
        do{
            String content = scanner.nextLine();
            if(content.equals("-0")){
                return;
            }
            clientSocket.send(new TextMessage(null, clientSocket.getId(),serverId + "-" + channelName,
                    MessageType.CHANNEL, LocalDateTime.now(), content));
            try {
                Response response = clientSocket.getReceiver().getResponse();
                if (!response.isAccepted()) {
                    System.err.println("Access denied to send the message");
                    System.err.println(response.getMessage());
                }
            }
            catch(ResponseNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
        while(true);
    }

    public GetPinnedMsgRes getPinnedMessage(){
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();
        System.out.println("enter server id : ");
        serverId = scanner.nextLine();

        try {
            clientSocket.send(new GetPinnedMsgReq(clientSocket.getId(), serverId, channelName));
            Response response = clientSocket.getReceiver().getResponse();
            if(response == null){
                System.err.println("No valid response was receiver from server");
                return null;
            }
            else if(response instanceof GetPinnedMsgRes){
                if(response.isAccepted()){

                    //show pinned message
                }
                else{
                    System.err.println("asking for getting pinned messages was rejected by server");
                    System.err.println(response.getMessage());
                    return null;
                }
            }

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;

    }


    private void showPinnedMessages(GetPinnedMsgRes getPinnedMsgRes){
        LinkedList<Message> PMessages = getPinnedMsgRes.getPinnedMessages();

        System.out.println("Pinned messages : ");
        for(Message i : PMessages){
            System.out.println(i);
        }
        System.out.println("\n");
    }

    public void createChannel(){
        System.out.println("to be back, enter '-0'");
        System.out.println("enter channel name :");
        channelName = scanner.nextLine();
        if(channelName.equals("-0")) return;
        System.out.println("enter server id : ");
        serverId = scanner.nextLine();

        System.out.println("type : \n");
        System.out.println("[1]-Text");
        System.out.println("[2]-Voice");

        ChannelType channelType;

        int choice = getOptionalInput(1, 2);

        if(choice == 1){
            channelType = ChannelType.TEXT;
        }
        else{
            channelType = ChannelType.VOICE;
        }

        if(serverId.equals("-0")) return;

        clientSocket.send(new CreateChannelReq(clientSocket.getId(), serverId,
                channelName, channelType));

        try {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getMessage());

        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteChannel(){
        System.out.println("to be back, enter '-0'");
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();

        if(channelName.equals("-0")) return;


        System.out.println("enter server id : ");
        serverId = scanner.nextLine();
        if(serverId.equals("-0")) return;

        clientSocket.send(new DeleteChannelReq(clientSocket.getId(), serverId, channelName));
        try {
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getMessage());
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    public void addUserChannel(){
        System.out.println("enter server id : ");
        serverId = scanner.nextLine();
        System.out.println("enter channel name : ");
        channelName = scanner.nextLine();
        System.out.println("enter userId : ");
        userId = scanner.nextLine();

        try{
            clientSocket.send(new AddUserChannelReq(clientSocket.getId(), serverId,
                    channelName, userId));
            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println("user added successfully.");
            }
            else{
                System.err.println("Access denied to add to user to the channel");
                System.err.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
