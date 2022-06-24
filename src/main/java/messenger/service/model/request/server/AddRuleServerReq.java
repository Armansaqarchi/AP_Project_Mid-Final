/**
 * this class is used to add a rule in server
 *
 * @author mahdi
 */
package messenger.service.model.request.server;

import messenger.service.model.server.Rule;

public class AddRuleServerReq extends ServerReq
{
    private final Rule rule;

    public AddRuleServerReq(String senderId, String serverId, Rule rule) {
        super(senderId, ServerRequestType.ADD_RULE, serverId);
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }
}
