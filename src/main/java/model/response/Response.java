package model.response;

import model.Transferable;

public class Response implements Transferable
{
    //id of who receives this response
    private final String receiverId;

    //specifies that the request is accepted or not
    private final boolean isAccepted;

    //verification of request or description about failure of request
    private final String message;

    public Response(String receiverId, boolean isAccepted, String message)
    {
        this.receiverId = receiverId;
        this.isAccepted = isAccepted;
        this.message = message;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiverId()
    {
        return receiverId;
    }
}
