package model.exception;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String cause){
        super(cause);
    }
}
