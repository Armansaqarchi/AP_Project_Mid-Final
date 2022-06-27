package messenger.service.model.message;

public class MessageReaction
{
    private String senderId;
    private Reaction reaction;

    public MessageReaction(String senderId, Reaction reaction) {
        this.senderId = senderId;
        this.reaction = reaction;
    }
}
