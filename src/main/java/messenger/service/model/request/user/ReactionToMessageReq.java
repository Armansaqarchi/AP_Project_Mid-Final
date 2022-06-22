/**
 * to reaction to a message
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

import messenger.service.model.message.Reaction;

import java.util.UUID;

public class ReactionToMessageReq extends UserRequest
{
    private UUID messageId;

    Reaction reaction;
}
