package model.response.channel;

import model.message.Message;
import model.response.GetInfoRes;

import java.util.LinkedList;

public class GetChatHistoryRes extends GetInfoRes
{
    private final LinkedList<Message> messages;

    public GetChatHistoryRes(String receiverId, boolean isAccepted,
                             String message,
                             LinkedList<Message> messages) {
        super(receiverId, isAccepted, message);
        this.messages = messages;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }
}
