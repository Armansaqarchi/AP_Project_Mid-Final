package client;

import model.Transferable;
import model.exception.InvalidObjectException;
import model.exception.ResponseNotFoundException;
import model.message.FileMsgNotification;
import model.message.Message;
import model.response.Response;

import java.util.Scanner;

public class Receiver
{
    //the latest response that was arrived from server
    private Response response;



    public void getInput(Transferable transferable) throws InvalidObjectException {

        try
        {
            setResponse((Response) transferable);

        }
        catch (ClassCastException e)
        {
            try
            {

                receiveMessage((Message) transferable);

            }
            catch (ClassCastException ex)
            {
                throw new InvalidObjectException();
            }

        }
    }

    private void setResponse(Response response)
    {
        this.response = response;
    }

    public Response getResponse() throws ResponseNotFoundException
    {
        for (int i = 0; i < 1000; i++)
        {
            if (this.response != null)
            {
                Response response = this.response;

                //setting the response field to null, to store next responses
                this.response = null;

                return response;
            }
            else
            {
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new ResponseNotFoundException();
    }

    private void receiveMessage(Message message)
    {
        System.out.println(message);

        //store messages in client side
        FileHandler.getFileHandler().saveMessage(message);

        if(message instanceof FileMsgNotification)
        {
            System.out.println("to get file you need id of this message.");
            System.out.println("message's id : " + message.getId());
        }
    }
}
