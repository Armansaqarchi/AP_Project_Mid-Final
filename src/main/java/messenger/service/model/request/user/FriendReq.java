/**
 * this class is used to send friend request to a user
 *
 * @author mahdi
 */
package messenger.service.model.request.user;

import messenger.service.model.request.Request;
import messenger.service.model.request.RequestType;

import java.util.Objects;
import java.util.UUID;

public class FriendReq extends UserRequest
{
    private UUID id;
    private String receiver;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof FriendReq)) return false;
        FriendReq friendReq = (FriendReq) o;
        return id.equals(friendReq.id);
    }


    public FriendReq(String senderId, UUID id, String receiver) {
        super(senderId, UserRequestType.FRIEND_REQ);
        this.id = id;
        this.receiver = receiver;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


}