package messenger.api;

import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.request.Authentication.AuthenticationReq;
import messenger.service.model.request.Channel.ChannelReq;
import messenger.service.model.request.Request;
import messenger.service.model.request.server.ServerReq;
import messenger.service.model.request.user.UserRequest;

public class RequestReceiver
{
    private static RequestReceiver requestReceiver;

    public static RequestReceiver getRequestReceiver()
    {
        if(null == requestReceiver)
        {
            requestReceiver = new RequestReceiver();
        }

        return requestReceiver;
    }

    private RequestReceiver()
    {
        userApi = new UserApi();
        authenticationApi = new AuthenticationApi();
        channelApi = new ChannelApi();
        serverApi = new ServerApi();
    }

    private final UserApi userApi;
    private final AuthenticationApi authenticationApi;
    private final ChannelApi channelApi;
    private final ServerApi serverApi;

    public void getRequest(Request request) throws InvalidTypeException {
        switch (request.getType())
        {
            case USER -> userApi.getRequest((UserRequest) request);
            case AUTHENTICATION -> authenticationApi.getRequest((AuthenticationReq) request);
            case CHANNEL -> channelApi.getRequest((ChannelReq) request);
            case SERVER -> serverApi.getRequest((ServerReq) request);
            default -> throw new InvalidTypeException();
        }
    }
}
