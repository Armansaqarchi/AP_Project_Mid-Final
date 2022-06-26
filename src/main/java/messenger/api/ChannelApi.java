package messenger.api;

import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.InvalidTypeException;
import messenger.service.model.message.FileMessage;
import messenger.service.model.message.Message;
import messenger.service.model.message.TextMessage;
import messenger.service.model.request.Channel.*;

public class ChannelApi
{

    public void getRequest(ChannelReq request) throws InvalidTypeException {
        switch(request.subType())
        {
            case REMOVE_USER -> removeUser((RemoveUserChannelReq) request);
            case ADD_USER -> addUser((AddUserChannelReq) request);
            case PIN_MESSAGE -> pinMessage((PinMessageReq) request);
            case CREAT_CHANNEL -> creatChannel((CreatChannelReq) request);
            case DELETE_CHANNEL -> deleteChannel((DeleteChannelReq) request);
            case RENAME_CHANNEL -> renameChannel((RenameChannelReq) request);
            case GET_CHAT_HISTORY -> getChatHistory((GetChatHistoryReq) request);
            case GET_PINNED_MESSAGES -> getPinnedMessage((GetPinnedMsgReq) request);
            default -> throw new InvalidTypeException();
        }
    }
    private void addUser(AddUserChannelReq request)
    {

    }

    private void creatChannel(CreatChannelReq request)
    {

    }

    private void deleteChannel(DeleteChannelReq request)
    {

    }

    private void getChatHistory(GetChatHistoryReq request)
    {

    }

    private void pinMessage(PinMessageReq request)
    {

    }

    private void getPinnedMessage(GetPinnedMsgReq request)
    {

    }

    private void removeUser(RemoveUserChannelReq request)
    {

    }

    private void renameChannel(RenameChannelReq request)
    {

    }
}
