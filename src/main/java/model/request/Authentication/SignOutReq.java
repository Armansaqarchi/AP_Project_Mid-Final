package model.request.Authentication;

public class SignOutReq extends AuthenticationReq
{
    public SignOutReq(String senderId, AuthenticationReqType subType, String id,
                      String password) {
        super(senderId, AuthenticationReqType.SIGN_OUT, id, password, null);
    }
}
