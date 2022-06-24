package messenger.service.model.message;

public enum MessageType
{
    TEXT_SERVER("textServer") , FILE_SERVER("fileServer") ,
    TEXT_PRIVATE("textPrivate") , FILE_PRIVATE("filePrivate");

    private final String value;

    private MessageType(String value){
        this.value = value;
    }

    public static MessageType getNameFromValue(String value){
        switch (value){
            case "textServer":
                return MessageType.TEXT_SERVER;
            case "textPrivate":
                return MessageType.TEXT_PRIVATE;
            case "filePrivate":
                return MessageType.FILE_PRIVATE;
            case "fileServer":
                return MessageType.FILE_SERVER;
        }

        return null;
    }

    public static String getValueFromName(MessageType messageType){
        switch(messageType.name()){
            case "FILE_PRIVATE" :
                return "filePrivate";
            case "FILE_SERVER":
                return "fileServer";
            case "TEXT_SERVER":
                return "textServer";
            case "TEXT_PRIVATE":
                return "textPrivate";

        }

        return null;
    }


}
