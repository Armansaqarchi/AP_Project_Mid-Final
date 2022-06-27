package messenger.service.model.server;

import java.util.HashSet;

public class Rule
{
    private String id;
    private HashSet<RuleType> rules;

    public HashSet<RuleType> getRules() {
        return rules;
    }
}
