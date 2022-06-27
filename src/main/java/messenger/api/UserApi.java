

package messenger.api;


import messenger.service.UserService;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.exception.ServerThreadNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.request.user.*;

import java.util.LinkedList;
import java.util.UUID;

public class UserApi
{
    private final UserService service;

    //sender object to send responses to client
    private final Sender sender;

    public UserApi()
    {
        service = new UserService();
        sender = Sender.getSender();
    }

    public void getRequest(UserRequest request) throws InvalidTypeException {
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
            default -> throw new InvalidTypeException();
        }
    }

    private void getFriendReqList(GetFriendReqList request)
    {

    }
    private void answerFriendReq(AnswerFriendReq request)
    {

    }

    private void blockUser(BlockUserReq request)
    {

    }

    private void friendReq(FriendReq request)
    {
        //setting a random uuid for friend request
        request.setId(UUID.randomUUID());
    }

    private void getFriendListReq(GetFriendListReq request)
    {

    }

    private void getMyProfile(GetMyProfileReq request)
    {

    }

    private void getUserProfile(GetUserProfileReq request)
    {

    }

    private void reactionToMessage(ReactionToMessageReq request)
    {

    }

    private void setMyProfile(SetMyProfileReq request)
    {

    }

    private void getBlockedUsers(GetBlockedUsersReq request)
    {

    }

    private void getServers(GetServersReq request)
    {

    }

    private void getPrivateChats(GetPrivateChatsReq request)
    {

    }
}
