package model.request.server;

public class SetServerImageReq extends ServerReq
{
    private final byte[] image;

    public SetServerImageReq(String senderId,  String serverId, byte[] image) {
        super(senderId, ServerRequestType.SET_IMAGE, serverId);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
