package model.response;

public class GetFileMsgRes extends GetInfoRes
{
    private final byte[] file;

    public GetFileMsgRes(String receiverId, boolean isAccepted, String message, byte[] file) {
        super(receiverId, isAccepted, message);
        this.file = file;
    }

    public byte[] getFile()
    {
        return file;
    }
}
