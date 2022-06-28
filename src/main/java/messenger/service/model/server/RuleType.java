package messenger.service.model.server;

import java.util.LinkedList;

public enum RuleType
{
    CREATE_CHANNEL("creat channel"), DELETE_CHANNEL("delete channel"),
    REMOVE_MEMBER("Remove member"), RENAME_CHANNEL("rename channel"),

    //remove member of channel
    RESTRICT_MEMBER("Restrict member"),
    RENAME_SERVER("Rename server"), SET_IMAGE("set image") ,
    WATCH_HISTORY("Watch server"), PIN_MESSAGE("Pin message");
    private final String value;

    private RuleType(String value)
    {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
