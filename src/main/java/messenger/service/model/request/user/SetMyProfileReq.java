/**
 * to set or edit users profile
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

public class SetMyProfileReq extends UserRequest
{
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String profileImage;
}
