package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.user.UserStatus;

import java.util.HashMap;

public class GetFriendListRes extends GetInfoRes
{
    private HashMap<String , UserStatus> friendList;
}
