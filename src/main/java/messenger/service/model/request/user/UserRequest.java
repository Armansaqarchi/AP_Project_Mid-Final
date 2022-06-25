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
    private UserRequestType type;

    public UserRequestType subType()
    {
        return type;
    }


    public UserRequest(String senderId, RequestType type, UserRequestType type1) {
        super(senderId, type);
        this.type = type1;
    }
}
