package client.controller.consoleController;

import client.ClientSocket;
import menus.TerminalMenu;

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
     * @param start
     * @param end
     * @return
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
