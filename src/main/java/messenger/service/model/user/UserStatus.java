package messenger.service.model.user;

public enum UserStatus
{

    ONLINE("Online") , IDLE("Idle") ,
    DO_NOT_DISTURB("Do not disturb"), INVISIBLE("Invisible");

    private final String value;

    private UserStatus(String value)
    {
        this.value = value;
    }
}
