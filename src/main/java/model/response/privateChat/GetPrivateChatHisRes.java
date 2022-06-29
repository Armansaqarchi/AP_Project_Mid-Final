package model.response.privateChat;

import model.message.Message;
import model.response.Response;

import java.util.LinkedList;

public class GetPrivateChatHisRes extends Response
{
    private final LinkedList<Message> messages;

    public GetPrivateChatHisRes(String receiverId, boolean isAccepted,
                                String message, LinkedList<Message> messages) {
        super(receiverId, isAccepted, message);
        this.messages = messages;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }
}
