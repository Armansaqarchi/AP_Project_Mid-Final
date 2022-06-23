package messenger.api;

import messenger.service.model.request.Authentication.AuthenticationReq;
import messenger.service.model.request.Channel.ChannelReq;
import messenger.service.model.request.Request;
import messenger.service.model.request.server.ServerReq;
import messenger.service.model.request.user.UserRequest;

public class RequestReceiver
{
//    public static RequestHandler requestHandler;
//
//    public static void getRequestHandler()
//    {
//
//    }
//
//    private RequestHandler()
//    {
//        userApi = new UserApi();
//        authenticationApi = new AuthenticationApi();
//    }

    private UserApi userApi;
    private AuthenticationApi authenticationApi;
    private ChannelApi channelApi;
    private ServerApi serverApi;

    public void getRequest(Request request)
    {
        switch (request.getType())
        {
            case USER -> userApi.getRequest((UserRequest) request);
            case AUTHENTICATION -> authenticationApi.getRequest((AuthenticationReq) request);
            case CHANNEL -> channelApi.getRequest((ChannelReq) request);
            case SERVER -> serverApi.getRequest((ServerReq) request);
            //default -> trow invalid type exception
        }
    }
}
