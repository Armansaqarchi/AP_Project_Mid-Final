package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;
import messenger.service.model.user.ServerIDs;

import java.util.LinkedList;

public class GetServersRes extends GetInfoRes
{
    private final LinkedList<ServerIDs> servers;

    public GetServersRes(String receiverId, boolean isAccepted, String message,
                         LinkedList<ServerIDs> servers) {
        super(receiverId, isAccepted, message);
        this.servers = servers;
    }
}
