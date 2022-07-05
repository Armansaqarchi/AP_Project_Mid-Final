/**
 * to get profile of a user
 *
 * @author mahdi
 */

package model.request.user;

public class GetUserProfileReq extends UserRequest
{
    private final String usersId;

    public GetUserProfileReq(String senderId, String usersId) {
        super(senderId, UserRequestType.GET_USER_PROFILE);
        this.usersId = usersId;
    }

    public String getUsersId()
    {
        return usersId;
    }
}
