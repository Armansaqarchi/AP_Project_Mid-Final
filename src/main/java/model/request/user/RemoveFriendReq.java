package model.request.user;

public class RemoveFriendReq extends UserRequest
{
    private final String userId;

    public RemoveFriendReq(String senderId, String userId)
    {
        super(senderId, UserRequestType.REMOVE_FRIEND);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
