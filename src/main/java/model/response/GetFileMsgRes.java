package model.response;

public class GetFileMsgRes extends GetInfoRes
{

    private final String fileName;
    private final byte[] content;

    public GetFileMsgRes(String receiverId, boolean isAccepted, String message,
                         String fileName , byte[] file)
    {
        super(receiverId, isAccepted, message);
        this.fileName = fileName;
        this.content = file;
    }

    public byte[] getContent()
    {
        return content;
    }

    public String getFileName() {
        return fileName;
    }
}
