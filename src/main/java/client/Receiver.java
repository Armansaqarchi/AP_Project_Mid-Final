package client;

import messenger.service.model.Transferable;
import messenger.service.model.exception.InvalidObjectException;
import messenger.service.model.exception.ResponseNotFoundException;
import messenger.service.model.message.Message;
import messenger.service.model.response.Response;

public class Receiver
{
    //the latest response that was arrived from server
    private Response response;

    public void getInput(Transferable transferable) throws InvalidObjectException {

        if(transferable instanceof Message)
        {
            //incomplete
        }
        else if(transferable instanceof Response)
        {
            setResponse((Response) transferable);
        }
        else
        {
            throw new InvalidObjectException();
        }
    }

    private void setResponse(Response response)
    {
        this.response = response;
    }

    private Response getResponse() throws ResponseNotFoundException
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
