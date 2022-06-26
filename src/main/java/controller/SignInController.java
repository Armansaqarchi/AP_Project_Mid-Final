package controller;

import java.util.Scanner;
import java.util.regex.*;

public class SignInController extends InputHandler {


    private String email;
    private String password;



    public void getUserDetails(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("in order to be back, press '1' ");
        System.out.println("Please enter email and password: ");
        System.out.print("[1] email : ");
        this.email = scanner.nextLine();

        if(this.email.equals("1")){
            //exit the method and return to the main menu
        }

        System.out.print("[2] password : ");
        this.password = scanner.nextLine();

        if(this.password.equals("1")){
            //do the same thing
        }

        //invokes another method that checks the validity of these two


    }
}
