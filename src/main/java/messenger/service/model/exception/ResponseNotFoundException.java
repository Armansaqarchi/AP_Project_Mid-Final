package messenger.service.model.exception;

public class ResponseNotFoundException extends Exception
{
    public ResponseNotFoundException(){
        super("no response was received from server");
    }
}
