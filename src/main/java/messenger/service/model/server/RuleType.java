package messenger.service.model.server;

public enum RuleType
{
    CREATE_CHANNEL("Create channel"), DELETE_CHANNEL("delete channel"),
    REMOVE_MEMBER("Remove member"), RESTRICT_MEMBER("Restrict member"),
    BAN_SERVER("Ban server"), CHANGE_SERVER("Change server"),
    WATCH_HISTORY("Watch server"), PIN_MESSAGE("Pin message"),
    ADD_MEMBER("Add member");
    private final String value;

    private RuleType(String value)
    {
        this.value = value;
    }
}
