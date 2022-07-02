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


    /**
     * gets parameter transferable and then checks the type of it
     * @param transferable, the response received from server
     * @throws InvalidObjectException, if type of object is invalid
     */
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

    /**
     * response from server might have some delay
     * because of that, there is a loop in the method runs after 50 m seconds
     * which updates the socked to see if anything is received from server.
     * @return response received from server
     * @throws ResponseNotFoundException, if the response time out occurs
     */
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
    }
}
