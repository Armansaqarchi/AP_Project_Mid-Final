package model.server;

import java.util.LinkedList;
import java.util.UUID;

public class VoiceChannel extends Channel
{


    public VoiceChannel(UUID id, String name, ChannelType type) {
        super(id, name, type);
    }

    public VoiceChannel(UUID id, String name, ChannelType type, LinkedList<String> users) {
        super(id, name, type, users);
    }
}
