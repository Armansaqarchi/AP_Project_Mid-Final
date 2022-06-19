/**
 * this class is used to answer a friend request that
 * has been sent to a user
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

import messenger.service.model.request.Request;

public class AnswerFriendReq extends Request
{
    private String receiver;
    private boolean isAccepted;
}
