package messenger.service.model.response.server;

import messenger.service.model.response.GetInfoRes;

public class GetServerImageRes extends GetInfoRes
{
    private final byte[] image;

    public GetServerImageRes(String receiverId, boolean isAccepted,
                             String message, byte[] image) {
        super(receiverId, isAccepted, message);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}