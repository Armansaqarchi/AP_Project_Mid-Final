/**
 * to reaction to a message
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

import messenger.service.model.message.Reaction;
import messenger.service.model.request.Request;

import java.util.UUID;

public class ReactionToMessageReq extends Request
{
    private UUID messageId;

    Reaction reaction;
}
