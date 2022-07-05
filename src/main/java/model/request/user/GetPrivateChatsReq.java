package model.request.user;

/**
 * to get private chats of a user
 *
 * @author mahdi
 */
public class GetPrivateChatsReq extends UserRequest
{
    public GetPrivateChatsReq(String senderId) {
        super(senderId, UserRequestType.GET_PRIVATE_CHATS);
    }
}
