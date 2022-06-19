/**
 * this class is used to block a user using its id
 *
 * @author mahdi
 */
package messenger.service.model.request.user;

import messenger.service.model.request.Request;

public class BlockUserReq extends Request
{
    //user id of who is blocked
    private String userid;
}
