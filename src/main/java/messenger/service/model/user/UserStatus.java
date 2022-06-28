package messenger.service.model.user;

import messenger.service.model.message.MessageType;

public enum UserStatus
{

    ONLINE("Online") , OFFLINE("Offline") , IDLE("Idle") ,
    DO_NOT_DISTURB("Do not disturb"), INVISIBLE("Invisible");

    private final String value;

    private UserStatus(String value)
    {
        this.value = value;
    }

    public static UserStatus getValueFromStatus(String value){
        switch(value){
            case "Online":
                return UserStatus.ONLINE;

            case "Offline":
                return UserStatus.OFFLINE;

            case "Idle":
                return UserStatus.IDLE;

            case "Do not disturb":
                return UserStatus.DO_NOT_DISTURB;

            case "Invisible":
                return UserStatus.INVISIBLE;

        }

        return null;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
