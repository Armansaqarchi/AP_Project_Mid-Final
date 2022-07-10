package model.message;

import java.io.Serial;
import java.io.Serializable;

/**
 * this class is used to send a notification of file message
 */
public class FileMsgNotification extends Message implements Serializable
{

    private final String fileName;

    //file's length in bytes
    private final long fileSize;

    /**
     * the constructor of class that
     * makes notification for a file message
     */
    public FileMsgNotification(FileMessage message)
    {
        super(message.getId() , message.getSenderId() , message.getReceiverId() ,
                message.getType() , message.getDate() , message.getReactions());
        fileName = message.getFileName();
        fileSize = message.getContent().length;
    }

    /**
     * @return messages information as a string
     */
    @Override
    public String toString()
    {

       if(MessageType.CHANNEL == getType())
       {
           String[] id = getReceiverId().split("-");

           return "\033[0;34mfile message from : "+ getSenderId() +
                   "\nserverId : " + id[0] + " channel name : " + id[1] +
                   "\nfile name : " + fileName +
                   "\nsize : " + fileSize + " bytes." +
                   "\nReactions : " + showReactions() +
                    "id : " + getId() +"\033[0m";
       }
       else
       {
           return "\033[0;34mfile message from : "+ getSenderId() +
                   "\nfile name : " + fileName +
                   "\nsize : " + fileSize + " bytes." +
                   "\nReactions : " + showReactions() +
                   "id : " + getId() +"\033[0m";
       }
    }

    /**
     * returns the files name and its size
     * @return
     */
    @Override
    public Object getContent()
    {
        return "file message , size : " + fileSize + " bytes.";
    }

    /**
     * @return the files size
     */
    public long getFileLength() {
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }
}