package messenger.service.model.response.privateChat;

import messenger.service.model.response.Response;

import java.util.LinkedList;
import java.util.UUID;

public class GetPrivateChatHisRes extends Response
{
    private final LinkedList<UUID> messages;

    public GetPrivateChatHisRes(String receiverId, boolean isAccepted,
                                String message, LinkedList<UUID> messages) {
        super(receiverId, isAccepted, message);
        this.messages = messages;
    }
}
