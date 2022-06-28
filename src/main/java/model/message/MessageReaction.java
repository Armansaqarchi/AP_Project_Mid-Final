package model.message;

public class MessageReaction
{
    private String senderId;
    private Reaction reaction;

    public MessageReaction(String senderId, Reaction reaction) {
        this.senderId = senderId;
        this.reaction = reaction;
    }



    @Override
    public String toString() {
        return senderId + " " + reaction + ", ";
    }
}
