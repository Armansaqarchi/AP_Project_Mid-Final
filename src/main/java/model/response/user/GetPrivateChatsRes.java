package model.response.user;

import model.response.GetInfoRes;

import java.util.LinkedList;

public class GetPrivateChatsRes extends GetInfoRes
{
    private final LinkedList<String> privateChats;

    public GetPrivateChatsRes(String receiverId, boolean isAccepted, String message,
                              LinkedList<String> privateChats) {
        super(receiverId, isAccepted, message);
        this.privateChats = privateChats;
    }

    public LinkedList<String> getPrivateChats() {
        return privateChats;
    }
}
