package messenger.service.model.request.Authentication;

import messenger.api.connection.ServerThread;
import messenger.service.model.request.Authentication.AuthenticationReq;

public class SignupReq extends AuthenticationReq
{
    private final String password;

    private final String name;
    private final String email;
    private final String phoneNumber;

    private final byte[] profileImage;

    public SignupReq(String senderId, String id, String password, ServerThread serverThread,
                      String name, String email,
                     String phoneNumber, byte[] profileImage)
    {
        super(senderId, AuthenticationReqType.SIGNUP, id, password, serverThread);
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }
}
