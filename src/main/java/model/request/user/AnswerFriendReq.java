/**
 * this class is used to answer a friend request that
 * has been sent to a user
 *
 * @author mahdi
 */

package model.request.user;

import java.util.UUID;

public class AnswerFriendReq extends UserRequest
{
    private final UUID friendRequest;
    private final boolean isAccepted;

    public AnswerFriendReq(String senderId,
                           UUID friendRequest, boolean isAccepted)
    {
        super(senderId, UserRequestType.ANSWER_FRIEND_REQ);
        this.friendRequest = friendRequest;
        this.isAccepted = isAccepted;
    }

    public UUID getFriendRequest() {
        return friendRequest;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
