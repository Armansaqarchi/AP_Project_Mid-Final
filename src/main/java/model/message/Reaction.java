package model.message;

/**
 * types of reaction to message
 */
public enum Reaction
{
    LIKE("like") , DISLIKE("dislike") , LAUGH("laugh");

    private final String value;

    private Reaction(String value)
    {
        this.value = value;
    }
}
