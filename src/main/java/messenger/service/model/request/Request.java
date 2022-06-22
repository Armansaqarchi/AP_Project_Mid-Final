/**
 * this class is used to get requests from user
 * it simulates requests that user sends to server
 *
 * @author mahdi
 */

package messenger.service.model.request;

public abstract class Request
{
    //id of user who sent request
    private String senderId;

    private RequestType type;
}