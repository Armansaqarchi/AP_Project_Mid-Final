/**
 * this class is used to send friend request to a user
 *
 * @author mahdi
 */
package messenger.service.model.request.user;

import messenger.service.model.request.Request;

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
}