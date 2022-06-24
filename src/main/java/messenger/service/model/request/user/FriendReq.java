/**
 * this class is used to send friend request to a user
 *
 * @author mahdi
 */
package messenger.service.model.request.user;

import messenger.service.model.request.Request;

import java.io.Serializable;
import java.util.UUID;

public class FriendReq extends UserRequest implements Serializable
{
    private final UUID friendRequest;
    private final String receiver;

    public FriendReq(String senderId,
                     UUID friendRequest, String receiver) {
        super(senderId, UserRequestType.FRIEND_REQ);
        this.friendRequest = friendRequest;
        this.receiver = receiver;
    }

    public UUID getFriendRequest()
    {
        return friendRequest;
    }

    public String getReceiver()
    {
        return receiver;
    }
}