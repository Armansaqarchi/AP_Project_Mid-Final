package messenger.service.model.response.user;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.response.Response;
import messenger.service.model.user.UserStatus;

import java.util.Arrays;

public class GetMyProfileRes extends GetInfoRes
{
    private final String id;
    private final String name;
    private final String password;
    private final String email;
    private final String phoneNumber;

    private final byte[] profileImage;

    private final UserStatus userStatus;

    public GetMyProfileRes(String receiverId, boolean isAccepted,
                           String message, String id, String name,
                           String password, String email, String phoneNumber,
                           byte[] profileImage, UserStatus userStatus) {
        super(receiverId, isAccepted, message);
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.userStatus = userStatus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public String toString() {
        return "id : " + id +
                "\nname : " + name +
                "\npassword : " + password +
                "\nemail : " + email +
                "\nphoneNumber : " + phoneNumber +
                "\nuserStatus : " + userStatus;
    }
}
