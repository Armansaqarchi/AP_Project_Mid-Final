/**
 * to set or edit users profile
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

public class SetMyProfileReq extends UserRequest
{
    private final String id;
    private final String name;
    private final String password;
    private final String email;
    private final String phoneNumber;

    private final byte[] profileImage;

    public SetMyProfileReq(String senderId, String id, String name, String password,
                           String email, String phoneNumber, byte[] profileImage)
    {
        super(senderId, UserRequestType.SET_PROFILE);
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
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
}
