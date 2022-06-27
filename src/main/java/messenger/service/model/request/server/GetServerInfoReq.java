package messenger.service.model.request.server;

public class GetServerInfoReq extends ServerReq
{
    public GetServerInfoReq(String senderId, String serverId) {
        super(senderId, ServerRequestType.GET_IMAGE, serverId);
    }
}
