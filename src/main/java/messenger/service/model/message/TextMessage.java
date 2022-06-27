package messenger.service.model.message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

public class TextMessage extends Message
{
    private String content;

    public TextMessage(UUID id, String senderId, String receiverId,
                       LocalDateTime date, LinkedList<MessageReaction> reactions,
                       String content) {

        super(id, senderId, receiverId, MessageType.PRIVATE_CHAT , date, reactions);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    @Override
    public String showMessage(){
        return "\n" + getSenderId() + " : \n" +
                getContent() + "            " +
                "Reactions : " + showMessageReactions() + "\n";
    }
}
