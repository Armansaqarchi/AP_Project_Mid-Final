package messenger.service.model.response;

public abstract class GetInfoRes extends Response
{

    public GetInfoRes(String receiverId, boolean isAccepted, String message) {
        super(receiverId, isAccepted, message);
    }
}
