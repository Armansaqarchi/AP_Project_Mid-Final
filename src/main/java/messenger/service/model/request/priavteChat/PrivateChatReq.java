package messenger.service.model.request.priavteChat;

import messenger.service.model.request.Request;
import messenger.service.model.request.RequestType;

public abstract class PrivateChatReq extends Request
{
    private final PrivateChatReqType subType;

    public PrivateChatReq(String senderId, PrivateChatReqType subType)
    {
        super(senderId, RequestType.PRIVATE_CHAT);
        this.subType = subType;
    }

    public PrivateChatReqType subType()
    {
        return subType;
    }
}
