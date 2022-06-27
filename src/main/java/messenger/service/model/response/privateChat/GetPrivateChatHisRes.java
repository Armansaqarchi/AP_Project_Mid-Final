package messenger.service.model.response.privateChat;

import messenger.service.model.message.Message;
import messenger.service.model.response.Response;

import java.util.LinkedList;
import java.util.UUID;

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
