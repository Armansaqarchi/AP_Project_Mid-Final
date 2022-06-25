package messenger.service.model.request.server;

/**
 * this class is used to get all rules of a server
 */
public class GetRulesServerReq extends ServerReq
{
    public GetRulesServerReq(String senderId,  String serverId) {
        super(senderId, ServerRequestType.GET_RULES, serverId);
    }
}
