package model.response.user;

import model.response.GetInfoRes;
import model.user.ServerIDs;

import java.util.LinkedList;

public class GetServersRes extends GetInfoRes
{
    private final LinkedList<ServerIDs> servers;

    public GetServersRes(String receiverId, boolean isAccepted, String message,
                         LinkedList<ServerIDs> servers) {
        super(receiverId, isAccepted, message);
        this.servers = servers;
    }

    public LinkedList<ServerIDs> getServers() {
        return servers;
    }
}
