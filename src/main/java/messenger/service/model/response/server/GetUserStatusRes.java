package messenger.service.model.response.server;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.user.UserStatus;

import java.util.HashMap;

public class GetUserStatusRes extends GetInfoRes
{
    private final HashMap<String , UserStatus> friendList;

    public GetUserStatusRes(String receiverId, boolean isAccepted,
                            String message, HashMap<String, UserStatus> friendList) {
        super(receiverId, isAccepted, message);
        this.friendList = friendList;
    }

    public HashMap<String, UserStatus> getFriendList() {
        return friendList;
    }
}
