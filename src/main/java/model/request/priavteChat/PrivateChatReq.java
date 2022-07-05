package model.request.priavteChat;

import model.request.Request;
import model.request.RequestType;

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
