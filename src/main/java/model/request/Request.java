/**
 * this class is used to get requests from user
 * it simulates requests that user sends to server
 *
 * @author mahdi
 */

package model.request;

import model.Transferable;

public abstract class Request implements Transferable
{
    //id of user who sent request
    private String senderId;
    private RequestType type;

    public Request(String senderId, RequestType type)
    {
        this.senderId = senderId;
        this.type = type;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public RequestType getType()
    {
        return type;
    }

}