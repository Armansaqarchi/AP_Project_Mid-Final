package messenger.service;

import messenger.dataBaseOp.Database;
import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.request.Authentication.LoginReq;
import messenger.service.model.request.Authentication.SignupReq;
import messenger.service.model.response.Response;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * this class is used to get and handle requests related to authentication
 *
 * @author mahdi
 */
public class AuthenticationService
{
    private final Database database;

    /**
     * the constructor
     */
    public AuthenticationService()
    {
        database = Database.getDatabase();
    }

    /**
     * handles login requests from client
     * @param request the login request
     * @return response related to the request
     */
    public Response login(LoginReq request)
    {
        try
        {
            //get user from database
            User user = database.getUserOp().findById(request.getId());

            //checking password
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
            //if user not found sends this response
            return new Response("" , false ,
                    e.getMessage() );
        }
        catch (SQLException | IOException |
            ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * handles signup requests
     * @param request the signup request
     * @return response related to request
     */
    public Response signup(SignupReq request)
    {
        //checking validity of inputted id
        if(!userExists(request.getId()))
        {
            try {
                //insert new user to database
                database.getUserOp().insertUser(new User(request.getId(),
                        request.getName(), request.getPassword(),
                        request.getEmail(), request.getPhoneNumber()));

                try
                {
                    //setting profile image
                    if(null != request.getProfileImage())
                    {
                        database.getUserOp().updateImage(request.getProfileImage(), request.getId());
                    }
                }
                catch (ConfigNotFoundException e)
                {
                    System.out.println(e.getMessage());
                }

                //return response
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
