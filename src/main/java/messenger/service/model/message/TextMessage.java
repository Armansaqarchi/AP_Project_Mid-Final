package messenger.service.model.message;

import java.io.Serializable;

public class TextMessage extends Message
{
    private String content;

    public TextMessage(String content) {
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
