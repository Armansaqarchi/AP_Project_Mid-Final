package messenger.service.model.request.user;

public class GetFriendReqList extends UserRequest
{
    public GetFriendReqList(String senderId, UserRequestType subType)
    {
        super(senderId, subType);
    }
}
