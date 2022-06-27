package messenger.service.model.response.server;

import messenger.service.model.response.GetInfoRes;

public class GetServerInfoRes extends GetInfoRes
{
    private final byte[] image;

    private final String id;
    private final String ownerId;
    private final String name;

    public GetServerInfoRes(String receiverId, boolean isAccepted,
                            String message, byte[] image,
                            String id, String ownerId, String name)
    {
        super(receiverId, isAccepted, message);
        this.image = image;
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }
}