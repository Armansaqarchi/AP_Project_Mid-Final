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

public class Receiver
{
    private static Receiver requestReceiver;

    private final UserApi userApi;
    private final AuthenticationApi authenticationApi;
    private final ChannelApi channelApi;
    private final ServerApi serverApi;

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
    }

    public void receive(Transferable transferable) throws InvalidTypeException, InvalidObjectException {

        if(transferable instanceof Request)
        {
            getRequest((Request) transferable);
        }
        else if(transferable instanceof Message)
        {
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

    private void getMessage(Message message) throws InvalidObjectException, InvalidTypeException {
        switch (message.getType())
        {
            case CHANNEL -> channelApi.getMessage(message);
            case PRIVATE_CHAT -> privateChatApi.getMessage(message);
            default -> throw new InvalidTypeException();
        }
    }
}
