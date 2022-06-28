/**
 * to rename server
 *
 * @author mahdi
 */

package model.request.server;

public class RenameServerReq extends ServerReq
{
    private final String newName;

    public RenameServerReq(String senderId,  String serverId, String newName) {
        super(senderId, ServerRequestType.RENAME_SERVER, serverId);
        this.newName = newName;
    }

    public String getNewName()
    {
        return newName;
    }
}
