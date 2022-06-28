package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.user.UserStatus;

import java.util.HashMap;

public class GetFriendListRes extends GetInfoRes
{
    private final HashMap<String , UserStatus> friendList;

    public GetFriendListRes(String receiverId, boolean isAccepted, String message, HashMap<String, UserStatus> friendList) {
        super(receiverId, isAccepted, message);
        this.friendList = friendList;
    }
}
