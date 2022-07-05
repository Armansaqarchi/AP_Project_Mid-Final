package model.request.user;

/**
 * to get name of servers of user
 *
 * @author mahdi
 */
public class GetServersReq extends UserRequest
{
    public GetServersReq(String senderId) {
        super(senderId, UserRequestType.GET_SERVERS);
    }
}
