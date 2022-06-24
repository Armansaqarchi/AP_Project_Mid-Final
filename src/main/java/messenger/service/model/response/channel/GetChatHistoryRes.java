package messenger.service.model.response.channel;

import messenger.service.model.message.Message;
import messenger.service.model.response.GetInfoRes;

import java.util.LinkedList;

public class GetChatHistoryRes extends GetInfoRes
{
    private LinkedList<Message> messages;
}
