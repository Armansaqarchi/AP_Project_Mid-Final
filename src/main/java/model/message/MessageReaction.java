package model.message;

/**
 * simulates a reaction to message
 */
public class MessageReaction
{
    private final String senderId;
    private final Reaction reaction;

    /**
     * @param senderId id of who makes this reaction
     * @param reaction the reaction type
     */
    public MessageReaction(String senderId, Reaction reaction) {
        this.senderId = senderId;
        this.reaction = reaction;
    }


    /**
     * @return messages information as a string
     */
    @Override
    public String toString() {
        return senderId + " " + reaction + ", ";
    }
}
