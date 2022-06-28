package model.exception;

public class UserNotFoundException extends ConfigNotFoundException{

    public UserNotFoundException(String config, String columnName){
        super(config, columnName, "user");
    }
}
