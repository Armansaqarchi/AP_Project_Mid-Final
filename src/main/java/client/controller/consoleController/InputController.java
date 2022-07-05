package client.controller.consoleController;

import client.ClientSocket;

import java.util.Scanner;

public abstract class InputController {

    protected ClientSocket clientSocket;
    protected static Scanner scanner;


    public InputController(ClientSocket clientSocket){
        scanner = new Scanner(System.in);
        this.clientSocket = clientSocket;
    }

    /**
     * takes an optional input from client, useful for menu handlers class and controllers class
     *
     * @param start, specifies start point of period of inputting
     * @param end, specifies end point of period of inputting
     * @return an int number which is clients selected option
     * @author Arman sagharchi
     */
    public static int getOptionalInput(int start, int end){
        int choice;

        while(true){
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if(choice >= start && choice <= end) {
                    break;
                }
                else{
                    System.err.println("choose between " + start + " and " + end);
                }
            }
            catch(NumberFormatException e){
                System.err.println("Invalid input");
            }

        }

        return choice;
    }



}
