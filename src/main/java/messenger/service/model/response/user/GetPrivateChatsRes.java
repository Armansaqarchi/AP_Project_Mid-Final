package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;

import java.util.LinkedList;

public class GetPrivateChatsRes extends GetInfoRes
{
    private final LinkedList<String> privateChats;

    public GetPrivateChatsRes(String receiverId, boolean isAccepted, String message,
                              LinkedList<String> privateChats) {
        super(receiverId, isAccepted, message);
        this.privateChats = privateChats;
    }
}
