package model.server;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * type of rules that a user can Rule can hold in for a user
 */
public enum RuleType implements Serializable
{
    CREATE_CHANNEL("Create channel"), DELETE_CHANNEL("Delete channel"),
    REMOVE_MEMBER("Remove member"), RENAME_CHANNEL("Rename channel"),

    //remove member of channel
    RESTRICT_MEMBER("Restrict member"),
    RENAME_SERVER("Rename server"), SET_IMAGE("Set image") ,
    WATCH_HISTORY("Get history"), PIN_MESSAGE("Pin message");
    private final String value;

    private RuleType(String value)
    {
        this.value = value;
    }

    /**
     * @return the value of rule
     */
    public String getValue() {
        return value;
    }
}
