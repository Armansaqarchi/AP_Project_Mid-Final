package client.controller;

import model.exception.InvalidEmailFormatException;
import model.exception.InvalidPasswordException;
import model.exception.InvalidPhoneNumberException;
import model.exception.InvalidUsernameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoVerifier {


    /**
     * checks the user validity
     * id must have a lower case and an UpperCase and a number
     * @param id, client id
     * @throws InvalidUsernameException, if the username has invalid format
     * @author Arman sagharchi
     */
    public static void checkUserValidity(String id) throws InvalidUsernameException {
        int size = id.length();

        //format pattern for the id
        Matcher matcher = Pattern.compile("[a-zA-Z0-9]+!*$").matcher(id);
        if(!matcher.find()){
            //if matcher did not find anything, it means its invalid
            throw new InvalidUsernameException("name must have only" +
                    " english characters and numbers");
        }

        if(size < 6){
            throw new InvalidUsernameException("username must have at least 6 characters");
        }

    }

    /**
     * checks the format validity of user
     * @param password, client pass
     * @throws InvalidPasswordException if password has invalidformat
     */
    public static void checkPasswordValidity(String password) throws InvalidPasswordException {
        int size = password.length();

        //format pattern of password
        Matcher matcher = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])").matcher(password);

        if(!matcher.find()){
            //means invalid format
            throw new InvalidPasswordException("password must have at least an " +
                    "uppercase and a lowercase and a number ");
        }

        if(size < 8){
            //means has less than 8 number
            throw new InvalidPasswordException("password must have at least 8 characters");
        }

    }


    /**
     * checks the email validity
     * @param email, email taken from client
     * @throws InvalidEmailFormatException, if the email has invalid format
     */
    public static void checkEmailValidity(String email)throws InvalidEmailFormatException {


        //regex to check the validity
        Matcher matcher = Pattern.compile(".+@gmail.com$").matcher(email);

        if(!matcher.find()){
            throw new InvalidEmailFormatException();
        }

    }

    public static void checkPhoneNumberValidity(String phoneNumber)
            throws InvalidPhoneNumberException {
        int size = phoneNumber.length();
        Matcher matcher = Pattern.compile("^09.+").matcher(phoneNumber);

        if(!matcher.find()){
            throw new InvalidPhoneNumberException("phone number starts with 09...");
        }

        if(size != 11){
            throw new InvalidPhoneNumberException("phone number must have exactly 11 characters");
        }

    }
}
