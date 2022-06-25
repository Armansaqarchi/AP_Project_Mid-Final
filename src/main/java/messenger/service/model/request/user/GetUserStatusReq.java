/**
 * to get a users status
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

public class GetUserStatusReq extends UserRequest
{
    private final String userId;

    public GetUserStatusReq(String senderId, String userId)
    {
        super(senderId, UserRequestType.GET_USER_STATUS);
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
}
