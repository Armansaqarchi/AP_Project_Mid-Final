package model.server;

/**
 * this class provides types of channel
 */
public enum ChannelType
{
    TEXT("text") , VOICE("voice");

    private final String value;


    private ChannelType(String value){
        this.value = value;
    }


    /**
     * returns ChannelType using its value
     * @param value the enums value
     * @return type of the value
     */
    public static ChannelType getTypeFromValue(String value){
        switch(value){
            case "voice":
                return ChannelType.VOICE;

            case "text":
                return ChannelType.TEXT;

        }

        return null;
    }

    /**
     * returns channels value from its type
     * @param channelType the enums name
     * @return the enums value
     */
    public static String getValueFromType(ChannelType channelType){
        switch(channelType.name()){
            case "VOICE" :
                return "voice";
            case "TEXT":
                return "text";

        }

        return null;
    }


}
