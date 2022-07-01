package model.server;

import java.io.Serializable;
import java.util.HashSet;

public class Rule implements Serializable
{
    private String id;
    private HashSet<RuleType> rules;

    public Rule(String id)
    {
        this.id = id;
        rules = new HashSet<>();
    }

    public HashSet<RuleType> getRules() {
        return rules;
    }

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
