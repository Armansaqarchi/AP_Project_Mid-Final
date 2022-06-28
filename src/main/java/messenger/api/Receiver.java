package messenger.api;

import messenger.service.model.Transferable;
import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.message.Message;
import messenger.service.model.request.Authentication.AuthenticationReq;
import messenger.service.model.request.Channel.ChannelReq;
import messenger.service.model.request.Request;
import messenger.service.model.request.priavteChat.PrivateChatReq;
import messenger.service.model.request.server.ServerReq;
import messenger.service.model.request.user.UserRequest;

import java.util.UUID;

public class Receiver
{
    private static Receiver requestReceiver;

    private final UserApi userApi;
    private final AuthenticationApi authenticationApi;
    private final ChannelApi channelApi;
    private final ServerApi serverApi;

    private final MessageApi messageApi;

    private final PrivateChatApi privateChatApi;
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

    public void receive(Transferable transferable) throws InvalidTypeException, InvalidObjectException {

        if(transferable instanceof Request)
        {
            getRequest((Request) transferable);
        }
        else if(transferable instanceof Message)
        {
            //setting a uuid for message
            ((Message) transferable).setId(UUID.randomUUID());

            getMessage((Message) transferable);
        }
        else
        {
            throw new InvalidObjectException();
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
            default -> throw new InvalidTypeException();
        }
    }

    private void getMessage(Message message) throws InvalidTypeException
    {
        messageApi.getMessage(message);
    }

    public void turnUserToOffline(String id)
    {
        userApi.turnUserToOffline(id);
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
