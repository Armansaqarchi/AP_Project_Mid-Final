/**
 * type of requests that are being sent and related to user
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

public enum UserRequestType
{
    ANSWER_FRIEND_REQ , BLOCK_USER , FRIEND_REQ ,
    GET_FRIEND_LIST , GET_MY_PROFILE , GET_USER_PROFILE ,
    GET_USER_STATUS , REACTION_TO_MESSAGE , SET_PROFILE ,
    GET_BLOCKED_USERS , GET_SERVERS , GET_PRIVATE_CHATS
}
