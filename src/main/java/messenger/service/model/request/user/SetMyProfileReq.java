/**
 * to set or edit users profile
 *
 * @author mahdi
 */

package messenger.service.model.request.user;

import messenger.service.model.request.Request;

public class SetMyProfileReq extends Request
{
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String profileImage;
}
