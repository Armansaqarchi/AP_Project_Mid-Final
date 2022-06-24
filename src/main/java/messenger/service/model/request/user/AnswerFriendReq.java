/**
 * this class is used to answer a friend request that
 * has been sent to a user
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

import java.util.UUID;

public class AnswerFriendReq extends UserRequest
{
    private UUID friendRequest;
    private boolean isAccepted;
}
