package model.message;

/**
 * this class is used to send a notification of file message
 */
public class FileMsgNotification extends Message
{
    private final String fileName;

    //file's length in bytes
    private final long fileSize;

    //makes notification for a file message
    public FileMsgNotification(FileMessage message)
    {
        super(message.getId() , message.getSenderId() , message.getReceiverId() ,
                message.getType() , message.getDate() , message.getReactions());
        fileName = message.getFileName();
        fileSize = message.getContent().length;
    }

    @Override
    public String toString()
    {

       if(MessageType.CHANNEL == getType())
       {
           String[] id = getReceiverId().split("-");

           return "file message from : "+ getSenderId() +
                   "\nserverId : " + id[0] + " channel name : " + id[1] +
                   "\nfile name : " + fileName +
                   "\nsize : " + fileSize + " bytes.";
       }
       else
       {
           return "file message from : "+ getSenderId() +
                   "\nfile name : " + fileName +
                   "\nsize : " + fileSize + " bytes.";
       }
    }

    @Override
    public Object getContent()
    {
        return "file message , size : " + fileSize + " bytes.";
    }

    public long getFileLength() {
        return fileSize;
    }
}