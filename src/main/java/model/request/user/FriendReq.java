/**
 * this class is used to send friend request to a user
 *
 * @author mahdi
 */
package model.request.user;

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