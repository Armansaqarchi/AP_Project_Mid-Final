/**
 * this class is used to block a user using its id
 *
 * @author mahdi
 */
package messenger.service.model.request.user;

public class BlockUserReq extends UserRequest
{
    //user id of who is blocked
    private final  String userId;

    public BlockUserReq(String senderId , String userId) {
        super(senderId, UserRequestType.BLOCK_USER);
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
}
