package messenger.service.model.request.Authentication;

import messenger.api.connection.ServerThread;
import messenger.service.model.request.Authentication.AuthenticationReq;

public class SignupReq extends AuthenticationReq
{

    private String name;
    private String email;
    private String phoneNumber;

    private byte[] profileImage;


    public SignupReq(AuthenticationReqType type, String id,
                     String password, ServerThread serverThread
                     , String name, String email
            , String phoneNumber, byte[] profileImage) {

        super(type, id, password, serverThread);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}
