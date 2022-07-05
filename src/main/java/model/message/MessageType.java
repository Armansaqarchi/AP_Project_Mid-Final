package model.message;

/**
 * types of messages in messenger
 */
public enum MessageType
{
    CHANNEL("channel"), PRIVATE_CHAT("privateChat");

    private final String value;

    private MessageType(String value){
        this.value = value;
    }

    /**
     * creates object from inputted value
     * @param value the types value
     * @return Message Type
     */
    public static MessageType getNameFromValue(String value){
        switch (value){
            case "channel":
                return MessageType.CHANNEL;
            case "privateChat":
                return MessageType.PRIVATE_CHAT;
        }

        return null;
    }

    /**
     * makes value of enum from its type
     * @param messageType the enum's name
     * @return value of the enum
     */
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
