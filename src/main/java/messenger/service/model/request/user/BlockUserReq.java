/**
 * this class is used to block a user using its id
 *
 * @author mahdi
 */
package messenger.service.model.request.user;

public class BlockUserReq extends UserRequest
{
    //user id of who is blocked
    private String userid;

    public BlockUserReq(UserRequestType type, String userid) {
        super(type);
        this.userid = userid;
    }
}
