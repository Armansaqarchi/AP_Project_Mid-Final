package messenger.service.model.request.server;

public class GetServerImageReq extends ServerReq
{
    public GetServerImageReq(String senderId,  String serverId) {
        super(senderId, ServerRequestType.GET_IMAGE, serverId);
    }
}
