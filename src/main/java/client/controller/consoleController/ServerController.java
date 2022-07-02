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
    //info s needed about a server and a client who is working on the server
    private String serverId;
    private String userId;
    private String name;
    private String fileName;

    private byte[] image;

    public ServerController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    /**
     * adds a rule for a user
     * first takes server id in which user is
     * @author mahdi kalhor
     */
    public void addRule()
    {
        System.out.println("Enter servers Id :");

        serverId = scanner.nextLine();

        //gets the target rule from client
        Rule rule = getRule();

        try{
            //sends the related req
            clientSocket.send(new AddRuleServerReq(clientSocket.getId() , serverId , rule));

            //takes the response
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

    /**
     * adds a user to the server
     * first takes the server id and user id and then sends the related req
     * @author mahdi kalhor
     */
    public void addUser()
    {
        //takes essential needs
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

    /**
     * takes server id and name of server and then creates the server
     * it will be created if response message takes the right to client
     * @author mahdi kalhor
     */
    public void creatServer()
    {
        //takes essential needs
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
            //sends the related req
            clientSocket.send(new CreateServerReq(clientSocket.getId() , serverId , name , image));

            //takes the response
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

    /**
     * takes server and deletes the server if client is the creator of server
     * @author mahdo kalhor
     */
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

    /**
     * takes server id and prints all the elders who have at least one rule
     * @author mahdi kalhor
     */
    public void getRules()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        try{
            //sends the related req
            clientSocket.send(new GetServerInfoReq(clientSocket.getId() , serverId));

            GetRulesServerRes response = (GetRulesServerRes) clientSocket.getReceiver().getResponse();
            if(response.isAccepted())
            {
                //takes the response and prints the rules
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

    /**
     * takes the server id and prints all the info s about a server
     * @author mahdi kalhor
     */
    public void getServerInfo()
    {
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        try{
            //sends the related req
            clientSocket.send(new GetServerInfoReq(clientSocket.getId() , serverId));

            //takes the response
            GetServerInfoRes response = (GetServerInfoRes) clientSocket.getReceiver().getResponse();
            if(response.isAccepted())
            {
                //if req is accepted by server, info s will be printed
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

    /**
     * takes a file name from client to set image
     * @author mahdi kalhor
     */
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
            //sends the related req
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

    /**
     * renames the server by taking id and new name
     * this is possible if the client has the right to rename it(has the rule)
     *
     * @author mahdi kalhor
     */
    public void renameServer()
    {
        //takes the needs about a server in order to specify
        System.out.println("Enter servers Id:");

        serverId = scanner.nextLine();

        System.out.println("Enter new name:");

        name = scanner.nextLine();

        try{
            //sends the related req
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

    /**
     * takes server id
     * @return al the users with theirs status
     * @author mahdi kalhor
     */
    private GetUserStatusRes getUserStatus(){
        //takes the needs
        System.out.println("to be back, enter '-0'");
        System.out.println("enter server id :");
        serverId = scanner.nextLine();

        if(serverId.equals("-0")) return null;

        //sends the req
        clientSocket.send(new GetUsersStatusReq(clientSocket.getId(), serverId));
        try{
            //takes the req and returns a response containing users status
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

    /**
     * gets user status response and then, takes the linked list in it,
     * then prints all the users with their status
     * @author mahdi kalhor
     */
    public void showUsersStatus(){
        GetUserStatusRes getUserStatusRes = getUserStatus();

        //if response is null, then something is wrong
        if(getUserStatusRes == null){
            return;
        }
        HashMap<String, UserStatus> users = getUserStatusRes.getUsers();

        //iterates and prints all the statuses
        for(String i : users.keySet()){
            System.out.println("" + i + "  ,Status : " + users.get(i));
        }
    }


    /**
     * takes user id and server id and removes the user from server
     * its possible if user exists and the client ahs the rule to remove the user
     * @author mahdi kalhor
     */
    public void removeUser(){

        //to take needs to remove a user from server
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

    /**
     * prints all the rules
     * @param rules, which has the rules in it
     * @author mahdi kalhor
     */
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

    /**
     * gets the server image
     * @return array bytes which contains the image
     * @author mahdi kalhor
     */
    private byte[] getImage()
    {
        //takes the file name
        System.out.println("Enter files name:");

        fileName = scanner.nextLine();

        FileInputStream stream = null;

        byte[] image;

        try

        {

            stream = new FileInputStream(fileName);

            image = new byte[(int) fileName.length()];

            //reads all the bytes from the file specified and writes it to image
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

    /**
     * takes user id and returns its rules
     * @return a Rule contains hashset rules of user
     * @author mahdi kalhor
     */
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

            switch (input = getOptionalInput(0 , ruleTypes.size()) - 1)
            {
                case -1 :
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

    /**
     * shows all the servers a user has
     * @param serverIDs, which takes it from next method and prints the servers with their info
     * @author mahdi kalhor
     */
    private void showServers(LinkedList<ServerIDs> serverIDs)
    {
        //iterates on the server id s and prints the info s about them
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

    /**
     * sends a show servers req to the server and gets the response from them
     * and then passes the linked list containing all the server ids to the previous method
     */
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
