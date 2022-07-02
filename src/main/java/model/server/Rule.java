package model.server;

import java.io.Serializable;
import java.util.HashSet;

/**
 * simulates a rule that added to server and lets the rule's owner
 * to edit server
 */
public class Rule implements Serializable
{
    private final String id;
    private final HashSet<RuleType> rules;

    public Rule(String id)
    {
        this.id = id;
        rules = new HashSet<>();
    }

    /**
     * @return the rules
     */
    public HashSet<RuleType> getRules() {
        return rules;
    }

    /**
     * @return the rules type as a string
     */
    public String getRulesString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("rules : ");

        for(RuleType ruleType : rules)
        {
            stringBuffer.append(ruleType.getValue() + ", ");
        }

        return stringBuffer.toString();
    }
}
