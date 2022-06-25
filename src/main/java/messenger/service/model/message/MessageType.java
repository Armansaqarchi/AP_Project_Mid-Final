package messenger.service.model.message;

public enum MessageType
{
    CHANNEL("channel"), PRIVATE_CHAT("privateChat");

    private final String value;

    private MessageType(String value){
        this.value = value;
    }

    public static MessageType getNameFromValue(String value){
        switch (value){
            case "channel":
                return MessageType.CHANNEL;
            case "privateChat":
                return MessageType.PRIVATE_CHAT;
        }

        return null;
    }

    public static String getValueFromName(MessageType messageType){
        switch(messageType.name()){
            case "PRIVATE_CHAT" :
                return "privateChat";
            case "CHANNEL":
                return "channel";

        }

        return null;
    }

}
