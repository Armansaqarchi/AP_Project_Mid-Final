package messenger.service.model.exception;

public class ConfigNotFoundException extends Exception {


    public ConfigNotFoundException(String config, String columnName, String entity){
        super("No " + entity + " found with " + columnName + " : " + config);
    }
}
