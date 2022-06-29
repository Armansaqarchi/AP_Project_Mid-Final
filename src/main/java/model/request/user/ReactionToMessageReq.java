/**
 * to reaction to a message
 *
 * @author mahdi
 */

package model.request.user;

import model.message.Reaction;

import java.util.UUID;

public class ReactionToMessageReq extends UserRequest
{
    private final UUID messageId;

    private final Reaction reaction;

    public ReactionToMessageReq(String senderId,  UUID messageId, Reaction reaction) {
        super(senderId, UserRequestType.REACTION_TO_MESSAGE);
        this.messageId = messageId;
        this.reaction = reaction;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public Reaction getReaction() {
        return reaction;
    }
}
