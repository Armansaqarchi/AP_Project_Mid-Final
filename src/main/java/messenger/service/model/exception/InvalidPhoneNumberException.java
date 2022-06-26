package messenger.service.model.exception;

public class InvalidPhoneNumberException extends Exception {

    public InvalidPhoneNumberException(String cause){
        super(cause);
    }
}
