/**
 * to get friends list of a user
 *
 * @author mahdi
 */

package model.request.user;

public class GetFriendListReq extends UserRequest
{
    public GetFriendListReq(String senderId) {
        super(senderId, UserRequestType.GET_FRIEND_LIST);
    }
}
