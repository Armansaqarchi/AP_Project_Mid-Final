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
                    e.getMessage() );
        }
        catch (SQLException | IOException |
            ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Response signup(SignupReq request)
    {
        if(!userExists(request.getId()))
        {
            try {
                database.getUserOp().insertUser(new User(request.getId(),
                        request.getName(), request.getPassword(),
                        request.getEmail(), request.getPhoneNumber()));

                try
                {
                    if(null != request.getProfileImage())
                    {
                        database.getUserOp().updateImage(request.getProfileImage(), request.getId());
                    }
                }
                catch (ConfigNotFoundException e)
                {
                    System.out.println(e.getMessage());
                }

                return new Response(request.getId(), true , "signed up successfully.");

            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

        }
        else
        {
            return new Response("" , false ,
                    "This user id is used before!");
        }
    }

    private boolean userExists(String id)
    {
        try
        {
            User user = database.getUserOp().findById(id);
            return true;
        }
        catch (ConfigNotFoundException e)
        {
            return false;
        }
        catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
