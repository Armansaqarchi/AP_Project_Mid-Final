package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;

import java.util.LinkedList;

public class GetPrivateChatsRes extends GetInfoRes
{
    private LinkedList<String> privateChats;
}
