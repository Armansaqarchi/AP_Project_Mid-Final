package messenger.service.model.server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public class TextChannel extends Channel
{
    private LinkedList<UUID> messages;
    private LinkedList<UUID> pinnedMessages;

    public TextChannel(UUID id, String name, ChannelType type) {
        super(id, name, type);
    }


    public TextChannel(UUID id, String name, ChannelType type, LinkedList<String> users,
                       LinkedList<UUID> messages, LinkedList<UUID> pinnedMessages) {

        super(id, name, type, users);
        this.messages = messages;
        this.pinnedMessages = pinnedMessages;
    }
}
