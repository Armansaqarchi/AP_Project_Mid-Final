/**
 * type of requests that are being sent and related to user
 *
 * @author mahdi
 */

package model.request.user;

public enum UserRequestType
{
    ANSWER_FRIEND_REQ("answerFriendReq") , BLOCK_USER("blockUser") , FRIEND_REQ("friendReq") ,
    GET_FRIEND_LIST("getFriendList") , GET_MY_PROFILE("getMyProfile") , GET_USER_PROFILE("getUserProfile") ,
    REACTION_TO_MESSAGE("reactionToMessage") , SET_PROFILE("setProfile") ,
    GET_BLOCKED_USERS("getBlockedUsers") , GET_SERVERS("getServers") , GET_PRIVATE_CHATS("getPrivateChats") ,
    GET_FRIEND_REQ_LIST("get FriendRequest list");

    private final String value;

    private UserRequestType(String value){
        this.value = value;
    }

    public static UserRequestType getNameFromValue(String value){
        switch (value){
            case "answerFriendReq":
                return UserRequestType.ANSWER_FRIEND_REQ;
            case "blockUser":
                return UserRequestType.BLOCK_USER;
            case "friendReq":
                return UserRequestType.FRIEND_REQ;
            case "getFriendList"  :
                return UserRequestType.GET_FRIEND_LIST;
            case "getMyProfile":
                return UserRequestType.GET_MY_PROFILE;
            case "getUserProfile":
                return UserRequestType.GET_USER_PROFILE;
            case "reactionToMessage":
                return UserRequestType.REACTION_TO_MESSAGE;
            case "setProfile":
                return UserRequestType.SET_PROFILE;
            case "getBlockedUsers":
                return UserRequestType.GET_BLOCKED_USERS;
            case "getServers":
                return UserRequestType.GET_SERVERS;
            case "getPrivateChats":
                return UserRequestType.GET_PRIVATE_CHATS;

        }

        return null;
    }

    public static String getValueFromName(UserRequestType messageType){
        switch(messageType.name()){
            case "ANSWER_FRIEND_REQ" :
                return "answerFriendReq";
            case "BLOCK_USER":
                return "blockUser";
            case "FRIEND_REQ":
                return "friendReq";
            case "GET_FRIEND_LIST":
                return "getFriendList";
            case "GET_MY_PROFILE":
                return "getMyProfile";
            case "GET_USER_PROFILE":
                return "getUserProfile";
            case "GET_USER_STATUS":
                return "getUserStatus";
            case "REACTION_TO_MESSAGE":
                return "reactionToMessage";
            case "SET_PROFILE":
                return "setProfile";
            case "GET_BLOCKED_USERS":
                return "getBlockedUsers";
            case "GET_SERVERS":
                return "getServers";
            case "GET_PRIVATE_CHATS":
                return "getPrivateChats";

        }

        return null;
    }


}
