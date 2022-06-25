package messenger.service.model.request.user;

/**
 * to get blocked list of user
 *
 * @author mahdi
 */
public class GetBlockedUsersReq extends UserRequest
{
    public GetBlockedUsersReq(String senderId) {
        super(senderId, UserRequestType.GET_BLOCKED_USERS);
    }
}
