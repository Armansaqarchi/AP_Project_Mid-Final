package model.response.user;

import model.response.GetInfoRes;
import model.user.UserStatus;

import java.util.HashMap;

public class GetFriendListRes extends GetInfoRes
{
    private final HashMap<String , UserStatus> friendList;

    public GetFriendListRes(String receiverId, boolean isAccepted, String message, HashMap<String, UserStatus> friendList) {
        super(receiverId, isAccepted, message);
        this.friendList = friendList;
    }

    public HashMap<String, UserStatus> getFriendList() {
        return friendList;
    }
}
