package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;
import messenger.service.model.user.UserStatus;

public class GetUserProfileRes extends GetInfoRes
{
    private final String id;
    private final String name;

    private final byte[] profileImage;

    private final UserStatus userStatus;

    public GetUserProfileRes(String receiverId, boolean isAccepted,
                             String message, String id, String name,
                             byte[] profileImage, UserStatus userStatus) {
        super(receiverId, isAccepted, message);
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.userStatus = userStatus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}
