package messenger.service.model.response.server;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.user.UserStatus;

import java.util.HashMap;

public class GetUserStatusRes extends GetInfoRes
{
    private final HashMap<String , UserStatus> users;

    public GetUserStatusRes(String receiverId, boolean isAccepted,
                            String message, HashMap<String, UserStatus> users) {
        super(receiverId, isAccepted, message);
        this.users = users;
    }

    public HashMap<String, UserStatus> getUsers() {
        return users;
    }
}
