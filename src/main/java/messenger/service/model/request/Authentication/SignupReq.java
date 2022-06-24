package messenger.service.model.request.Authentication;

import messenger.service.model.request.Authentication.AuthenticationReq;

public class SignupReq extends AuthenticationReq
{
    private String confirmPassword;

    private String name;
    private String email;
    private String phoneNumber;

    private byte[] profileImage;
}
