package client.controller.consoleController;

import client.ClientSocket;
import model.exception.ResponseNotFoundException;
import model.request.server.*;
import model.request.user.GetServersReq;
import model.response.Response;
import model.response.server.GetRulesServerRes;
import model.response.server.GetServerInfoRes;
import model.response.server.GetUserStatusRes;
import model.response.user.GetServersRes;
import model.server.Rule;
import model.server.RuleType;
import model.user.ServerIDs;
import model.user.UserStatus;

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
            if(!response.isAccepted())
            {
                System.out.println("\033[0;31mAccess denied to add rule.\033[0m");
                System.out.println(response.getMessage());
            }

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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

            if(!response.isAccepted()){
                System.out.println("\033[0;31mAccess denied to add user.\033[0m");
            }

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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

        System.out.println("Enter 1 in order to set image : (enter another key otherwise)");

        if(scanner.nextLine().equals("1"))
        {
            image = getImage();
        }

        try{
            clientSocket.send(new CreateServerReq(clientSocket.getId() , serverId , name , image));

            Response response = clientSocket.getReceiver().getResponse();
            if(!response.isAccepted()){
                System.out.println("\033[0;31mFailed to creat the server.\033[0m");
            }

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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

            if(!response.isAccepted()){
                System.out.println("\033[0;31mAccess denied to delete the server.\033[0m");
            }

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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
                System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

                printRules(response.getRules());

            }
            else{
                System.out.println("\033[0;31mAccess denied to get the server's rules.");
                System.out.println(response.getMessage() + "\033[0m");
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
                System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
                System.out.println(response);
            }
            else{
                System.out.println("\033[0;31mAccess denied to get server's information.");
                System.out.println(response.getMessage() + "\033[0m");
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

            if(!response.isAccepted()){
                System.out.println("\033[0;31mAccess denied to change the server's image.\033[0m");
            }

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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

            if(!response.isAccepted()){
                System.out.println("\033[0;31mAccess denied to change the server's name.\033[0m");
            }

            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private GetUserStatusRes getUserStatus(){
        System.out.println("to be back, enter '-0'");
        System.out.println("enter server id :");
        serverId = scanner.nextLine();

        if(serverId.equals("-0")) return null;

        clientSocket.send(new GetUsersStatusReq(clientSocket.getId(), serverId));
        try{
            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted() && response instanceof GetUserStatusRes){
                return (GetUserStatusRes) response;
            }
            else{
                System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;


    }

    public void showUsersStatus(){
        GetUserStatusRes getUserStatusRes = getUserStatus();

        if(getUserStatusRes == null){
            return;
        }
        HashMap<String, UserStatus> users = getUserStatusRes.getUsers();

        for(String i : users.keySet()){
            System.out.println("" + i + "Status : " + users.get(i));
        }
    }


    public void removeUser(){
        System.out.println("to be back, enter  '-0'");
        System.out.println("enter user id : ");

        userId = scanner.nextLine();
        if(userId.equals("-0")) return;

        System.out.println("enter server id :");
        serverId = scanner.nextLine();

        clientSocket.send(new RemoveUserServerReq(clientSocket.getId(), serverId, userId));

        try{
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
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

    public void getServers()
    {
        try{
            clientSocket.send(new GetServersReq(clientSocket.getId()));

            GetServersRes response = (GetServersRes)clientSocket.getReceiver().getResponse();

            if(response.isAccepted())
            {
                System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");

                //print servers
                showServers(response.getServers());
            }
            else
            {
                System.out.println("\033[0;31m" + response.getMessage() + "\033[0m");
            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
