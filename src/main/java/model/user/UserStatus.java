package model.user;

/**
 * types of users status of user
 */
public enum UserStatus
{

    ONLINE("Online") , OFFLINE("Offline") , IDLE("Idle") ,
    DO_NOT_DISTURB("Do not disturb"), INVISIBLE("Invisible");

    private final String value;

    private UserStatus(String value)
    {
        this.value = value;
    }

    /**
     * creates a users status from its value
     * @param value the value of enum
     * @return the enum related to value
     */
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

    /**
     * @return users value
     */
    @Override
    public String toString()
    {
        return value;
    }
}
