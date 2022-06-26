package messenger.service.model.exception;

public class InvalidUsernameException extends Exception{

    public InvalidUsernameException(String cause){
        super(cause);
    }
}
