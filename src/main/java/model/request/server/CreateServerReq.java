/**
 * to creat server
 *
 * @author mahdi
 */

package model.request.server;

public class CreateServerReq extends ServerReq
{
    private final String name;

    private final byte[] image;

    public CreateServerReq(String senderId, String serverId, String name, byte[] image) {
        super(senderId, ServerRequestType.CREAT_SERVER, serverId);
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }
}
