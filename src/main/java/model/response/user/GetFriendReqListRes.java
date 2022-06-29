package model.response.user;

import model.response.Response;

import java.util.LinkedList;
import java.util.UUID;

public class GetFriendReqListRes extends Response
{
    private final LinkedList<UUID> friendRequests;

    public GetFriendReqListRes(String receiverId, boolean isAccepted,
                               String message, LinkedList<UUID> friendRequests)
    {
        super(receiverId, isAccepted, message);
        this.friendRequests = friendRequests;
    }
}
