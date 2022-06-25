package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;

public class GetUserStatusRes extends GetInfoRes
{
    private final String id;
    private final String status;

    public GetUserStatusRes(String receiverId, boolean isAccepted,
                            String message, String id, String status) {
        super(receiverId, isAccepted, message);
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
