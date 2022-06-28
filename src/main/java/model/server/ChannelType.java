package model.server;

public enum ChannelType
{
    TEXT("text") , VOICE("voice");

    private final String value;


    private ChannelType(String value){
        this.value = value;
    }


    public static ChannelType getTypeFromValue(String value){
        switch(value){
            case "voice":
                return ChannelType.VOICE;

            case "text":
                return ChannelType.TEXT;

        }

        return null;
    }

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
