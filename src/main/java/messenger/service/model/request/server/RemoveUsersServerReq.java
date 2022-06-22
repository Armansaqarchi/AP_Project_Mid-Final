/**
 * remove users from server
 *
 * @author mahdi
 */

package messenger.service.model.request.server;

import java.util.LinkedList;

public class RemoveUsersServerReq extends ServerReq
{
    private LinkedList<String> userIds;
}
