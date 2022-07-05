package model.response.user;

import model.response.Response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class GetFriendReqListRes extends Response
{
    //maps the uuid of friend request to its sender id
    private final HashMap<UUID , String> friendRequests;

    public GetFriendReqListRes(String receiverId, boolean isAccepted,
                               String message, HashMap<UUID , String> friendRequests)
    {
        super(receiverId, isAccepted, message);
        this.friendRequests = friendRequests;
    }

    public HashMap<UUID , String> getFriendRequests() {
        return friendRequests;
    }
}
