package client;

import model.message.Message;
import model.message.MessageType;
import model.response.GetFileMsgRes;
import model.response.channel.GetChatHistoryRes;
import model.response.privateChat.GetPrivateChatHisRes;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileHandler
{
    private final String messageUrl;
    private final String fileUrl;

    public FileHandler(String id)
    {
        fileUrl = "client/" + id + "/message";
        messageUrl = "client/" + id + "/file";
        new File(messageUrl).mkdirs();
        new File(fileUrl).mkdirs();
    }

    private void saveMessage(Message message)
    {
        try
        {
            LinkedList<Message> messages = null;

            String fileName;

            //for channel messages
            if(MessageType.CHANNEL == message.getType())
            {
                fileName = message.getReceiverId();
            }
            //for private chat messages
            else
            {
                fileName = message.getSenderId();
            }

            File file = new File(messageUrl + '/' + fileName);

            //checking that file is exists or not
            if(file.createNewFile())
            {
                messages = new LinkedList<>();
            }
            else
            {
                try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file)))
                {
                    messages = (LinkedList<Message>) inputStream.readObject();
                }
                catch (FileNotFoundException e)
                {
                    System.out.println(e.getMessage());;
                }
            }

            //write messages list in file if it wasn't null
            if(null != messages)
            {
                messages.add(message);

                try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file)))
                {
                    outputStream.writeObject(messages);
                }
                catch (FileNotFoundException e)
                {
                    System.out.println(e.getMessage());;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveMessage(LinkedList<Message> messages)
    {
        //if list was empty it will not be written in file
        if(messages.isEmpty())
        {
            return;
        }

        String fileName;

        Message sample = messages.get(0);

        //for channel messages
        if(MessageType.CHANNEL == sample.getType())
        {
            fileName = sample.getReceiverId();
        }
        //for private chat messages
        else
        {
            fileName = sample.getSenderId();
        }

        File file = new File(messageUrl + '/' + fileName);

        //creat or truncate the file to write the messages list
        try (PrintWriter pw = new PrintWriter(file))
        {

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file)))
        {
            outputStream.writeObject(messages);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());;
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(GetFileMsgRes response)
    {
        String url = fileUrl + '/' + response.getFileName();

        Path path = Paths.get(url);

        while (true)
        {

            try
            {
                Files.createFile(path);

                //write content into file
                Files.write(path , response.getContent());

                System.out.println("file saved as : " + url);

                return;
            }
            catch (FileAlreadyExistsException e)
            {
                //if file with same name exists it will a '0' will be added to file name
                path = Paths.get(url + '0');
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

    }
}
