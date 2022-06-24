package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;

import java.util.LinkedList;

public class GetBlockedUsersRes extends GetInfoRes
{
    //id of blocked users
    private LinkedList<String> blockedUsers;
}
