package messenger.service.model.server;

import java.util.LinkedList;
import java.util.UUID;

public class TextChannel extends Channel
{
    private LinkedList<UUID> messages;
    private LinkedList<UUID> pinnedMessages;
}
