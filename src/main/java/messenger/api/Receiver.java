package messenger.api;

import model.Transferable;
import model.exception.InvalidObjectException;
import model.exception.InvalidTypeException;
import model.message.Message;
import model.request.Authentication.AuthenticationReq;
import model.request.Channel.ChannelReq;
import model.request.GetFileMsgReq;
import model.request.Request;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.request.priavteChat.PrivateChatReq;
import model.request.server.ServerReq;
import model.request.user.UserRequest;
import model.user.UserStatus;

import java.util.UUID;

/**
 * receives requests or messages and sends it to their related apis
 */
public class Receiver
{
    private static Receiver requestReceiver;

    private final UserApi userApi;
    private final AuthenticationApi authenticationApi;
    private final ChannelApi channelApi;
    private final ServerApi serverApi;

    private final MessageApi messageApi;

    private final PrivateChatApi privateChatApi;

    /**
     * returns the receiver object
     * @return the receiver object
     */
    public static Receiver getReceiver()
    {
        if(null == requestReceiver)
        {
            requestReceiver = new Receiver();
        }

        return requestReceiver;
    }

    private Receiver()
    {
        userApi = new UserApi();
        authenticationApi = new AuthenticationApi();
        channelApi = new ChannelApi();
        serverApi = new ServerApi();
        privateChatApi = new PrivateChatApi();
        messageApi = new MessageApi();
    }

    /**
     * receives and handles a request or message
     * @param transferable the transferable object that got from client
     * @throws InvalidTypeException throw's if the objects type was invalid
     * @throws InvalidObjectException throw's if the inputted object was invalid
     */
    public void receive(Transferable transferable) throws InvalidTypeException, InvalidObjectException {

        try
        {

            getRequest((Request) transferable);
            System.out.println("dsafas");
        }
        catch (ClassCastException e)
        {
            try
            {
                //setting a uuid for message
                ((Message) transferable).setId(UUID.randomUUID());

                getMessage((Message) transferable);
            }
            catch (ClassCastException ex)
            {
                throw new InvalidObjectException();
            }

        }

    }
    private void getRequest(Request request) throws InvalidTypeException {
        switch (request.getType())
        {
            case USER -> userApi.getRequest((UserRequest) request);
            case AUTHENTICATION -> authenticationApi.getRequest((AuthenticationReq) request);
            case CHANNEL -> channelApi.getRequest((ChannelReq) request);
            case SERVER -> serverApi.getRequest((ServerReq) request);
            case PRIVATE_CHAT -> privateChatApi.getRequest((PrivateChatReq) request);
            case FILE_MESSAGE -> messageApi.getFileMsg((GetFileMsgReq) request);
            default -> throw new InvalidTypeException();
        }
    }

    private void getMessage(Message message) throws InvalidTypeException
    {
        messageApi.getMessage(message);
    }

    /**
     * turns user status to offline or online when its connected or disconnected
     * @param id the users id
     * @param status users status
     */
    public void turnUserStatus(String id , UserStatus status)
    {
        userApi.turnUserStatus(id , status);
    }

    /**
     * when a client is verified it is used to get unread messages
     * @param id the id of user
     */
    public void getUnreadMessages(String id)
    {
        messageApi.getUnreadMessages(id);
    }
}
