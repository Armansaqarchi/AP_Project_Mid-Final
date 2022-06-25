/**
 * to handle requests that are related to user
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

import messenger.service.model.request.Request;
import messenger.service.model.request.RequestType;

public abstract class UserRequest extends Request
{
    protected final UserRequestType subType;

    public UserRequest(String senderId, UserRequestType subType)
    {
        super(senderId, RequestType.USER);
        this.subType = subType;
    }

    public UserRequestType getSubType()
    {
        return subType;
    }



}
