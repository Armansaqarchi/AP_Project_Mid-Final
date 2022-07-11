package model.message;

import java.io.Serializable;

/**
 * types of reaction to message
 */
public enum Reaction implements Serializable
{
    LIKE("like") , DISLIKE("dislike") , LAUGH("laugh");

    private final String value;

    private Reaction(String value)
    {
        this.value = value;
    }
}
