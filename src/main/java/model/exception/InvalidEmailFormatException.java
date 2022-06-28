package model.exception;

public class InvalidEmailFormatException extends Exception {

    public InvalidEmailFormatException(){
        super("email must have a valid domain like @gmail.com");
    }
}
