package messenger.service.model.response.channel;

import messenger.service.model.message.Message;
import messenger.service.model.response.GetInfoRes;

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
