

package messenger.api;


import messenger.api.connection.ConnectionHandler;
import messenger.api.connection.ServerThread;
import messenger.service.UserService;
import model.exception.InvalidTypeException;
import model.exception.ServerThreadNotFoundException;
import model.request.user.*;
import model.response.Response;
import model.user.UserStatus;

import java.util.UUID;

/**
 * gets and handles requests related to user
 */
public class UserApi
{
    private final UserService service;

    //sender object to send responses to client
    private final Sender sender;

    /**
     * the constructor of class
     */
    protected UserApi()
    {
        service = new UserService();
        sender = Sender.getSender();
    }

    /**
     * gets and handles requests
     * @param request the request
     * @throws InvalidTypeException throws if inputted requests type was invalid
     */
    protected void getRequest(UserRequest request) throws InvalidTypeException {
        switch(request.subType())
        {
            case BLOCK_USER -> blockUser((BlockUserReq) request);
            case FRIEND_REQ -> friendReq((FriendReq) request);
            case GET_SERVERS -> getServers((GetServersReq) request);
            case SET_PROFILE -> setMyProfile((SetMyProfileReq) request);
            case GET_MY_PROFILE -> getMyProfile((GetMyProfileReq) request);
            case GET_FRIEND_LIST -> getFriendListReq((GetFriendListReq) request);
            case GET_USER_PROFILE -> getUserProfile((GetUserProfileReq) request);
            case ANSWER_FRIEND_REQ -> answerFriendReq((AnswerFriendReq) request);
            case GET_BLOCKED_USERS -> getBlockedUsers((GetBlockedUsersReq) request);
            case GET_PRIVATE_CHATS -> getPrivateChats((GetPrivateChatsReq) request);
            case REACTION_TO_MESSAGE -> reactionToMessage((ReactionToMessageReq) request);
            case GET_FRIEND_REQ_LIST -> getFriendReqList((GetFriendReqList) request);
            case UN_BLOCK -> unBlockUser((UnBlockUserReq) request);
            case REMOVE_FRIEND -> removeFriend((RemoveFriendReq) request);
            default -> throw new InvalidTypeException();
        }
    }

    private void getFriendReqList(GetFriendReqList request)
    {
        sendResponse(service.getFriendReqList(request));
    }
    private void answerFriendReq(AnswerFriendReq request)
    {
        sendResponse(service.answerFriendReq(request));
    }

    private void blockUser(BlockUserReq request)
    {
        sendResponse(service.blockUser(request));
    }

    private void friendReq(FriendReq request)
    {
        //setting a random uuid for friend request
        request.setId(UUID.randomUUID());

        sendResponse(service.friendReq(request));
    }

    private void getFriendListReq(GetFriendListReq request)
    {
        sendResponse(service.getFriendListReq(request));
    }

    private void getMyProfile(GetMyProfileReq request)
    {
        sendResponse(service.getMyProfile(request));
    }

    private void getUserProfile(GetUserProfileReq request)
    {
        sendResponse(service.getUserProfile(request));
    }

    private void reactionToMessage(ReactionToMessageReq request)
    {
        sendResponse(service.reactionToMessage(request));
    }

    private void setMyProfile(SetMyProfileReq request)
    {
        Response response = service.setMyProfile(request);

        //if id is changed it most be change in controller class
        if(response.isAccepted() && null != request.getId() && ! request.getSenderId().equals(request.getId()))
        {
            ServerThread serverThread = ConnectionHandler.getConnectionHandler().getServerThread(request.getSenderId());

            //change id of server thread
            if(null != serverThread)
            {
                serverThread.setId(request.getId());
            }

            //change id in connections list
            ConnectionHandler.getConnectionHandler().addConnection(request.getId() , serverThread);
        }

        sendResponse(response);
    }

    private void getBlockedUsers(GetBlockedUsersReq request)
    {
        sendResponse(service.getBlockedUsers(request));
    }

    private void getServers(GetServersReq request)
    {
        sendResponse(service.getServers(request));
    }

    private void getPrivateChats(GetPrivateChatsReq request)
    {
        sendResponse(service.getPrivateChats(request));
    }

    public void turnUserStatus(String id , UserStatus status)
    {
        service.turnUserStatus(id , status);
    }
    private void removeFriend(RemoveFriendReq request)
    {
        sendResponse(service.removeFriend(request));
    }

    private void unBlockUser(UnBlockUserReq request)
    {
        sendResponse(service.unBlockUser(request));
    }
    private void sendResponse(Response response)
    {
        try
        {
            sender.sendResponse(response);
        }
        catch (ServerThreadNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
