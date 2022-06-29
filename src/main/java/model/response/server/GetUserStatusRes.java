package model.response.server;

import model.response.GetInfoRes;
import model.user.UserStatus;

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
