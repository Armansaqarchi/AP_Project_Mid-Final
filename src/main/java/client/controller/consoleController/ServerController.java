package client.controller.consoleController;

import client.ClientSocket;
import model.exception.ResponseNotFoundException;
import model.request.server.*;
import model.request.user.GetServersReq;
import model.response.Response;
import model.response.server.GetRulesServerRes;
import model.response.server.GetServerInfoRes;
import model.response.user.GetServersRes;
import model.server.Rule;
import model.server.RuleType;
import model.user.ServerIDs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServerController extends InputController
{
    private String serverId;
    private String userId;
    private String name;
    private String fileName;

    private byte[] image;

    public ServerController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    public void addRule()
    {
        System.out.println("Enter servers Id :");

        serverId = scanner.nextLine();

        Rule rule = getRule();

        try{
            clientSocket.send(new AddRuleServerReq(clientSocket.getId() , serverId , rule));

            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println(response.getMessage());
            }
            else{
                System.out.println("Access denied to add rule.");

                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void addUser()
    {
        System.out.println("Enter servers Id :");

        serverId = scanner.nextLine();

        System.out.println("Enter user's id :");

        userId = scanner.nextLine();

        try{
            clientSocket.send(new AddUserServerReq(clientSocket.getId() , serverId , userId));

            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println(response.getMessage());
            }
            else{
                System.out.println("Access denied to add user.");

                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void creatServer()
    {
        System.out.println("Enter servers Id :");

        serverId = scanner.nextLine();

        System.out.println("Enter server's name :");
        
        name = scanner.nextLine();

        image = getImage();

        try{
            clientSocket.send(new CreateServerReq(clientSocket.getId() , serverId , name , image));

            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println(response.getMessage());
            }
            else{
                System.out.println("Failed to creat the server.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteServer()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        try{
            clientSocket.send(new DeleteServerReq(clientSocket.getId() , serverId));

            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println("server was successfully deleted.");
            }
            else{
                System.out.println("Access denied to delete the server.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void getRules()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        try{
            clientSocket.send(new GetServerInfoReq(clientSocket.getId() , serverId));

            GetRulesServerRes response = (GetRulesServerRes) clientSocket.getReceiver().getResponse();
            if(response.isAccepted())
            {
                System.out.println(response.getMessage());

                printRules(response.getRules());

            }
            else{
                System.out.println("Access denied to get the server's rules.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void getServerInfo()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        try{
            clientSocket.send(new GetServerInfoReq(clientSocket.getId() , serverId));

            GetServerInfoRes response = (GetServerInfoRes) clientSocket.getReceiver().getResponse();
            if(response.isAccepted())
            {
                System.out.println(response.getMessage());
                System.out.println(response);
            }
            else{
                System.out.println("Access denied to get server's information.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void setServerImage()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        image = getImage();

        if(null == image)
        {
            return;
        }

        try{
            clientSocket.send(new SetServerImageReq(clientSocket.getId() , serverId , image));

            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted()){
                System.out.println("server image was successfully changed.");
            }
            else
            {
                System.out.println("Access denied to change the server's image.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    public void renameServer()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        System.out.println("Enter new name:");

        name = scanner.nextLine();

        try{
            clientSocket.send(new RenameServerReq(clientSocket.getId() , serverId , name));

            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                System.out.println("serverName was successfully renamed to " + name);
            }
            else{
                System.out.println("Access denied to change the server's name.");
                System.out.println(response.getMessage());
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeUser(){
        System.out.println("to be back, enter  '-0'");
        System.out.println("enter user id : ");

        userId = scanner.nextLine();
        if(userId.equals("-0")) return;

        System.out.println("enter server id :");
        serverId = scanner.nextLine();

        clientSocket.send(new RemoveUserServerReq(clientSocket.getId(), serverId, userId));

        try{
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println(response.getMessage());
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    private void printRules(HashMap<String , Rule> rules)
    {
        ArrayList<String> keys = new ArrayList<>(rules.keySet());

        for(String key : keys)
        {
            System.out.println(key + " ");
            System.out.println(rules.get(key).getRulesString());
        }

        System.out.println();
    }

    private byte[] getImage()
    {
        System.out.println("Enter files name:");

        fileName = scanner.nextLine();

        FileInputStream stream = null;

        byte[] image;

        try

        {
            stream = new FileInputStream(fileName);

            image = new byte[(int) fileName.length()];

            stream.read(image);

            stream.close();

            return image;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Invalid file name!");
        }
        catch (IOException e)
        {
            System.out.println("can't open the file!");
        }

        return null;
    }

    private Rule getRule()
    {
        System.out.println("Enter user's id :");

        userId = scanner.nextLine();

        Rule rule = new Rule(userId);

        while (true)
        {
            System.out.println("choose a rule : (enter zero to finish)");

            ArrayList<RuleType> ruleTypes = new ArrayList<>(List.of(RuleType.values()));

            for (int i = 0 ; i < ruleTypes.size() ; i++)
            {
                System.out.println((i+1) + ". " + ruleTypes.get(i));
            }

            int input;

            switch (input = getOptionalInput(0 , ruleTypes.size()))
            {
                case 0 :
                {
                    return rule;
                }
                default:
                {
                    rule.getRules().add(ruleTypes.get(input));
                }
            }

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
}
