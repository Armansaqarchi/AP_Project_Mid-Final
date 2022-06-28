package client.controller.consoleController;

import client.ClientSocket;
import model.exception.ResponseNotFoundException;
import model.message.Message;
import model.request.Channel.GetChatHistoryReq;
import model.request.Channel.RemoveUserChannelReq;
import model.request.Channel.RenameChannelReq;
import model.response.Response;
import model.response.channel.GetChatHistoryRes;

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

    private void showChat(GetChatHistoryRes GChatHisRes){
        LinkedList<Message> messages = GChatHisRes.getMessages();

        for(Message message : messages){
            System.out.println(message.showMessage());
        }
    }

    public void ChannelChat(){

        System.out.println("want to get private chats ? ");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int choice = getOptionalInput(1,2);

        switch(choice){
            case 1 :

        }
    }
}
