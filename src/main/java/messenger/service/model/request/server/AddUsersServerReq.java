/**
 * to add users to server
 *
 * @author mahdi
 */

package messenger.service.model.request.server;

import java.util.LinkedList;

public class AddUsersServerReq extends ServerReq
{
    private LinkedList<String> userIds;
}
