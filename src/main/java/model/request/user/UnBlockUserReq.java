package model.request.user;

public class UnBlockUserReq extends UserRequest
{
    private final String userId;

    public UnBlockUserReq(String senderId, String userId)
    {
        super(senderId, UserRequestType.UN_BLOCK);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
