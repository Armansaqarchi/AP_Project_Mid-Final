package messenger.service.model.message;

public enum Reaction
{
    LIKE("likes") , DISLIKE("dislikes") , LAUGH("laughs");

    private final String value;

    private Reaction(String value)
    {
        this.value = value;
    }
}
