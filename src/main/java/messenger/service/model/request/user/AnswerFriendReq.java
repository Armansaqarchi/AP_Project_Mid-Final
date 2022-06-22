/**
 * this class is used to answer a friend request that
 * has been sent to a user
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

public class AnswerFriendReq extends UserRequest
{
    private String receiver;
    private boolean isAccepted;
}
