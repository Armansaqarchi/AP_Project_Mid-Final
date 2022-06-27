package messenger.service.model.request.priavteChat;

public class GetPrivateChatHisReq extends PrivateChatReq
{
    private final String userId;

    public GetPrivateChatHisReq(String senderId, String userId)
    {
        super(senderId, PrivateChatReqType.GET_CHAT_HISTORY);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
