package messenger.service.model.response.channel;

import messenger.service.model.message.Message;
import messenger.service.model.response.GetInfoRes;

import java.util.LinkedList;

public class GetPinnedMsgRes extends GetInfoRes
{
    private final LinkedList<Message> pinnedMessages;

    public GetPinnedMsgRes(String receiverId, boolean isAccepted,
                           String message,
                           LinkedList<Message> pinnedMessages) {
        super(receiverId, isAccepted, message);
        this.pinnedMessages = pinnedMessages;
    }

    public LinkedList<Message> getPinnedMessages() {
        return pinnedMessages;
    }
}
