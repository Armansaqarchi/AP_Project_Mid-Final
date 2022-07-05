package model.request.server;

import model.server.RuleType;

import java.util.LinkedList;

public class RemoveRuleReq extends ServerReq
{
    private final String userId;
    private final LinkedList<RuleType> rules;

    public RemoveRuleReq(String senderId , String serverId ,
                         String userId, LinkedList<RuleType> rules)
    {
        super(senderId, ServerRequestType.REMOVE_RULE , serverId);
        this.userId = userId;
        this.rules = rules;
    }

    public String getUserId() {
        return userId;
    }

    public LinkedList<RuleType> getRules() {
        return rules;
    }
}
