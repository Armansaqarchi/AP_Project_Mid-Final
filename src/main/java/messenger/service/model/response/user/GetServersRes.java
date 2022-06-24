package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;
import messenger.service.model.user.ServerIDs;

import java.util.LinkedList;

public class GetServersRes extends GetInfoRes
{
    private LinkedList<ServerIDs> servers;
}
