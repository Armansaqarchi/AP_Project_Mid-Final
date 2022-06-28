package client;

import messenger.service.model.Transferable;
import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.ResponseNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.request.Request;
import messenger.service.model.response.Response;

import java.util.UUID;

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

                Message message = (Message) transferable;

                //incomplete

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
        for (int i = 0; i < 100; i++)
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
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new ResponseNotFoundException();
    }
}
