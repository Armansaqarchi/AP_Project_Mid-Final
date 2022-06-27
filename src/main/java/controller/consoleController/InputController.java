package controller.consoleController;

import client.ClientSocket;

import java.util.Scanner;

public abstract class InputController {

    protected ClientSocket clientSocket;
    protected Scanner scanner;


    public InputController(ClientSocket clientSocket){
        scanner = new Scanner(System.in);
        this.clientSocket = clientSocket;
    }

    public int getOptionalInput(int start, int end){
        int choice;

        while(true){
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if(choice >= start && choice <= end) {
                    break;
                }
            }
            catch(NumberFormatException e){
                System.err.println("Invalid input");
            }

        }

        return choice;
    }



}
