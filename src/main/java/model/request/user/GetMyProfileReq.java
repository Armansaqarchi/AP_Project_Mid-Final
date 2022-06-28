/**
 * to get profile of this user
 *
 * @author mahdi
 */

package model.request.user;

public class GetMyProfileReq extends UserRequest
{
    public GetMyProfileReq(String senderId) {
        super(senderId, UserRequestType.GET_MY_PROFILE);
    }
}
