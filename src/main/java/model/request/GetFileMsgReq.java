package model.request;


import java.util.UUID;

public class GetFileMsgReq extends Request
{
    private final UUID messageId;

    public GetFileMsgReq(String senderId , UUID messageId)
    {
        super(senderId, RequestType.FILE_MESSAGE);
        this.messageId = messageId;
    }

    public UUID getMessageId() {
        return messageId;
    }
}
