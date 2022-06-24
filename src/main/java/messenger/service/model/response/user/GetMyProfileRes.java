package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;
import messenger.service.model.user.UserStatus;

public class GetMyProfileRes extends GetInfoRes
{
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    private byte[] profileImage;

    private UserStatus userStatus;
}
