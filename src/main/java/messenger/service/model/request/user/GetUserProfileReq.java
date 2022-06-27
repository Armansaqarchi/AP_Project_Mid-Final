/**
 * to get profile of a user
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

public class GetUserProfileReq extends UserRequest
{
    private String usersId;

    public GetUserProfileReq(UserRequestType type, String usersId) {
        super(type);
        this.usersId = usersId;
    }
}
