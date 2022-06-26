package messenger.service;

import messenger.api.Sender;
import messenger.dataBaseOp.Database;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.request.Authentication.LoginReq;
import messenger.service.model.request.Authentication.SignupReq;
import messenger.service.model.response.Response;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;

public class AuthenticationService
{
    Database database;

    public AuthenticationService()
    {
        database = Database.getDatabase();
    }

    public Response login(LoginReq request)
    {
        try
        {
            User user = database.getUserOp().findById(request.getId());

            if(user.getPassword().equals(request.getPassword()))
            {
                return  new Response(request.getId(), true ,
                        "you logged in successfully!");
            }
            else
            {
                return new Response("" , false ,
                        "wrong password!" );
            }
        }
        catch (ConfigNotFoundException  e )
        {
            return new Response("" , false ,
                    "user with id : '" +request.getId() + "' not found!" );
        }
        catch (SQLException | IOException |
            ClassNotFoundException e)
        {
            System.out.println();
            e.printStackTrace();
        }
        return null;
    }

    public Response signup(SignupReq request)
    {

        return null;
    }

    private boolean checkPassword()
    {
        //authentication failed exception
        return false;
    }

    private boolean checkId()
    {
        //authentication failed exception
        return false;
    }
}
