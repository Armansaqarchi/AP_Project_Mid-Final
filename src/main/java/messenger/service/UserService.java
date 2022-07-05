package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.dataBaseOp.UpdateType;
import model.exception.ConfigNotFoundException;
import model.message.MessageReaction;
import model.request.user.*;
import model.response.Response;
import model.response.user.*;
import model.user.User;
import model.user.UserStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

/**
 * this class gets and handles requests related to user
 */
public class UserService
{
    private final Database database;

    /**
     * the constructor
     */
    public UserService()
    {
        database = Database.getDatabase();
    }

    /**
     * handles request of answer friend request
     * @param request the answer friend request
     * @return response to request
     */
    public Response answerFriendReq(AnswerFriendReq request)
    {
        try
        {
            //removing friend request from users friend requests list
            database.getUserOp().updateList(UpdateType.REMOVE , "friend_requests" ,
                    request.getSenderId() , request.getFriendRequest());
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }

        if(request.isAccepted())
        {
            try
            {
                FriendReq friendReq = database.getFriendRequestOp().
                        findById(request.getFriendRequest().toString());

                //adding users to each other friends list
                database.getUserOp().updateList(UpdateType.ADD , "friend_list" ,
                        friendReq.getSenderId() , friendReq.getReceiver());

                database.getUserOp().updateList(UpdateType.ADD , "friend_list" ,
                        friendReq.getReceiver() ,friendReq.getSenderId());

                return new Response(request.getSenderId() , true ,
                        "user added to your friends.");
            }
            catch (ConfigNotFoundException e)
            {
                return new Response(request.getSenderId() , false , e.getMessage());
            }
            catch (IOException | SQLException | ClassNotFoundException e)
            {
                throw new RuntimeException();
            }

        }
        else
        {
            return new Response(request.getSenderId() , true ,
                    "answer friend request got.");
        }
    }

    /**
     * handles request of block a user request
     * @param request the answer friend request
     * @return response to request
     */
    public Response blockUser(BlockUserReq request)
    {
        try
        {
            database.getUserOp().updateList(UpdateType.ADD , "blocked_users" ,
                    request.getSenderId(), request.getUserId());

            //adding user to blocked list
            return new Response(request.getSenderId() , true ,
                    "user added to your block list.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of friend request
     * @param request the friend request
     * @return response to request
     */
    public Response friendReq(FriendReq request)
    {
        try
        {
            User user = database.getUserOp().findById(request.getReceiver());

            if(user.getBlockedUsers().contains(request.getSenderId()))
            {
                return new Response(request.getSenderId(), false ,
                        "you are blocked by this user!");
            }

            //add friend request to users friend requests list
            database.getUserOp().updateList(UpdateType.ADD , "friend_requests" ,
                    request.getReceiver(), request.getId());

            //inserting the friend request into data base
            database.getFriendRequestOp().insertFriendReq(request);

            //adding user to blocked list
            return new Response(request.getSenderId() , true ,
                    "friend request sent to user.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting friends list
     * @param request the request of getting friends list
     * @return response to request
     */
    public Response getFriendListReq(GetFriendListReq request)
    {
        try
        {
            User user = database.getUserOp().findById(request.getSenderId());

            //getting friends status
            HashMap<String , UserStatus> friends = getUsersStatus(user.getFriendList());

            return new GetFriendListRes(request.getSenderId() , true ,
                    "friend list sent." , friends);
        }
        catch (ConfigNotFoundException e)
        {
            return new GetFriendListRes(request.getSenderId() , false , e.getMessage() , null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting profile
     * @param request the request of getting profile
     * @return response to request
     */
    public Response getMyProfile(GetMyProfileReq request)
    {

        try
        {
            User user = database.getUserOp().findById(request.getSenderId());

            return new GetMyProfileRes(request.getSenderId() , true ,
                    "friend list sent." , user.getId(), user.getName(),
                    user.getPassword(), user.getEmail(), user.getPhoneNumber()
                    , user.getProfileImage(), user.getUserStatus());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetMyProfileRes(request.getSenderId() , false , e.getMessage()
                    , null , null , null , null , null , null , null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting another users profile
     * @param request the request of getting another users profile
     * @return response to request
     */
    public Response getUserProfile(GetUserProfileReq request)
    {
        try
        {
            User user = database.getUserOp().findById(request.getUsersId());

            if(user.getBlockedUsers().contains(request.getSenderId()))
            {
                return new GetUserProfileRes(request.getSenderId(), false ,
                        "you are blocked by this user!" , null ,
                        null , null , null);
            }

            return new GetUserProfileRes(request.getSenderId(), true ,
                    "users profile sent." , user.getId(), user.getName(),
                    user.getProfileImage(), user.getUserStatus());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetUserProfileRes(request.getSenderId(), false ,
                    e.getMessage(), null , null ,
                    null , null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of reaction to message
     * @param request the request of reaction to message
     * @return response to request
     */
    public Response reactionToMessage(ReactionToMessageReq request)
    {
        try
        {
            database.getMessageOp().updateReactions(UpdateType.ADD , request.getMessageId().toString() ,
                    new MessageReaction(request.getSenderId(), request.getReaction()));

            return new Response(request.getSenderId(), true ,
                    "reaction added successfully.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId(), false ,
                    e.getMessage());
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of setting profile
     * @param request the request of setting profile
     * @return response to request
     */
    public Response setMyProfile(SetMyProfileReq request)
    {
        try
        {
            if(null != request.getName())
            {
                database.getUserOp().updateProfile(request.getSenderId(),  "name" , request.getName());
            }

            if(null != request.getPassword())
            {
                database.getUserOp().updateProfile(request.getSenderId(),  "password" , request.getPassword());
            }

            if(null != request.getEmail())
            {
                database.getUserOp().updateProfile(request.getSenderId(),  "email" , request.getEmail());
            }

            if(null != request.getPhoneNumber())
            {
                database.getUserOp().updateProfile(request.getSenderId(),  "phone_number" , request.getPhoneNumber());
            }

            if(null != request.getProfileImage())
            {
                database.getUserOp().updateUserProfileImage(request.getProfileImage(), request.getSenderId());
            }

            if(null != request.getUserStatus())
            {
                database.getUserOp().updateProfile(request.getSenderId(),  "user_status" , request.getUserStatus().toString());
            }

            if(null != request.getId() && !request.getSenderId().equals(request.getId()))
            {
                if(database.getUserOp().isExists(request.getId()))
                {
                    return new Response(request.getSenderId(), false , "this id is used before!");
                }
                else
                {
                    database.getUserOp().updateProfile(request.getSenderId(),  "user_id" , request.getId());
                }
            }

            return new Response(request.getSenderId(), true ,
                    "profile changed successfully.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId(), false ,
                    e.getMessage());
        }
        catch (SQLException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting blocked list of user
     * @param request the request of getting blocked list of user
     * @return response to request
     */
    public Response getBlockedUsers(GetBlockedUsersReq request)
    {

        try
        {
            User user = database.getUserOp().findById(request.getSenderId());

            return new GetBlockedUsersRes(request.getSenderId() , true ,
                    "blocked list sent." , user.getBlockedUsers());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetBlockedUsersRes(request.getSenderId() , false , e.getMessage(), null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting servers list of user
     * @param request the request of getting servers list of user
     * @return response to request
     */
    public Response getServers(GetServersReq request)
    {

        try
        {
            User user = database.getUserOp().findById(request.getSenderId());

            return new GetServersRes(request.getSenderId() , true ,
                    "servers list sent." , user.getServers());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetServersRes(request.getSenderId() , false , e.getMessage(), null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting private chats list of user
     * @param request the request of getting private chats list of user
     * @return response to request
     */
    public Response getPrivateChats(GetPrivateChatsReq request)
    {
        try
        {
            User user = database.getUserOp().findById(request.getSenderId());

            return new GetPrivateChatsRes(request.getSenderId() , true ,
                    "private chats sent." , user.getPrivateChats());
        }
        catch (ConfigNotFoundException e)
        {
            return new GetPrivateChatsRes(request.getSenderId() , false , e.getMessage(), null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of getting friend requests list
     * @param request the request of getting friend requests list
     * @return response to request
     */
    public Response getFriendReqList(GetFriendReqList request)
    {
        try
        {
            User user = database.getUserOp().findById(request.getSenderId());

            return new GetFriendReqListRes(request.getSenderId() , true ,
                    "friend request list sent." , getFriendRequest(user.getFriendRequests()));
        }
        catch (ConfigNotFoundException e)
        {
            return new GetFriendReqListRes(request.getSenderId() , false , e.getMessage() , null);
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of removing friend requests
     * @param request the request of removing friend requests
     * @return response to request
     */
    public Response removeFriend(RemoveFriendReq request)
    {
        try
        {
            database.getUserOp().updateList(UpdateType.REMOVE , "friend_list" ,
                    request.getSenderId(), request.getUserId());

            database.getUserOp().updateList(UpdateType.REMOVE , "friend_list" ,
                    request.getUserId() , request.getSenderId());

            return new Response(request.getSenderId() , true ,
                    "user : " + request.getUserId() + " removed from your friends list.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    /**
     * handles request of unblock a user
     * @param request the request of unblock a user
     * @return response to request
     */
    public Response unBlockUser(UnBlockUserReq request)
    {
        try
        {
            database.getUserOp().updateList(UpdateType.REMOVE , "blocked_users" ,
                    request.getSenderId() , request.getUserId());

            return new Response(request.getSenderId() , true ,
                    "user : " + request.getUserId() + " removed from your blocked list.");
        }
        catch (ConfigNotFoundException e)
        {
            return new Response(request.getSenderId() , false , e.getMessage());
        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException();
        }
    }

    private HashMap<String, UserStatus> getUsersStatus(LinkedList<String> userIdes)
    {
        HashMap<String , UserStatus> usersStatus = new HashMap<>();

        for(String id : userIdes)
        {
            try
            {
                User user = database.getUserOp().findById(id);

                usersStatus.put(id , user.getUserStatus());
            }
            catch (ConfigNotFoundException e)
            {

            }
            catch (SQLException | IOException | ClassNotFoundException e)
            {
                throw new RuntimeException();
            }
        }

        return usersStatus;
    }

    private HashMap<UUID , String> getFriendRequest(LinkedList<UUID> requests)
    {
        HashMap<UUID , String> friendRequests = new HashMap<>();

        for(UUID uuid : requests)
        {
            try
            {
                FriendReq friendReq = Database.getDatabase().getFriendRequestOp().findById(uuid.toString());
                friendRequests.put(friendReq.getId() , friendReq.getSenderId());
            }
            catch (ConfigNotFoundException e)
            {

            }
            catch (IOException | SQLException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }

        }

        return friendRequests;
    }

    /**
     * changes users status
     * @param id the users id
     * @param status the new status
     */
    public void turnUserStatus(String id , UserStatus status)
    {
        try {
            database.getUserOp().updateProfile(id , "user_status" , status.toString());
        }
        catch (ConfigNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
