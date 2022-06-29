/**
 * to handle requests that are related to user
 *
 * @author mahdi
 */

package model.request.user;

import model.request.Request;
import model.request.RequestType;

public abstract class UserRequest extends Request
{
    protected final UserRequestType subType;

    public UserRequest(String senderId, UserRequestType subType)
    {
        super(senderId, RequestType.USER);
        this.subType = subType;
    }

    public UserRequestType subType()
    {
        return subType;
    }



}
