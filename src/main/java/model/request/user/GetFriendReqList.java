package model.request.user;

public class GetFriendReqList extends UserRequest
{
    public GetFriendReqList(String senderId)
    {
        super(senderId, UserRequestType.GET_FRIEND_REQ_LIST);
    }
}
