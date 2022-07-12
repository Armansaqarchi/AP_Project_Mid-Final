package model.server;

import java.util.LinkedList;
import java.util.UUID;

/**
 * simulates a text channel of server
 * users can share text or file messaeg over this channel
 */

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

    /**
     * @return messages that being sent in this channel
     */
    public LinkedList<UUID> getMessages() {
        return messages;
    }

    /**
     * @return messages that are pinned in this channel
     */
    public LinkedList<UUID> getPinnedMessages() {
        return pinnedMessages;
    }
}
