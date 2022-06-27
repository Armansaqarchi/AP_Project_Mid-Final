package messenger.service.model.message;

public class FileMessage extends Message
{
    private byte[] content;

    public FileMessage(byte[] content) {
        this.content = content;
    }

    @Override
    public String showMessage(){
        return "\n" + getSenderId() + " : \n" +
                "File Message" + "            " +
                "Reactions : " + showMessageReactions() + "\n";
    }
}
