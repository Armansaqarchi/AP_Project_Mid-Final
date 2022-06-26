package controller.consoleController;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class InputHandler {

    Scanner scanner;


    public InputHandler(){
        scanner = new Scanner(System.in);
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
