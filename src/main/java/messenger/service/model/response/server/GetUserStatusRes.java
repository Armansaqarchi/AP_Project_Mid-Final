package messenger.service.model.response.server;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.user.UserStatus;

import java.util.HashMap;

public class GetUserStatusRes extends GetInfoRes
{
    private HashMap<String , UserStatus> friendList;
}
