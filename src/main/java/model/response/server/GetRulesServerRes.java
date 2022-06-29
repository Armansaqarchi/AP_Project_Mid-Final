package model.response.server;

import model.response.GetInfoRes;
import model.server.Rule;

import java.util.HashMap;

public class GetRulesServerRes extends GetInfoRes
{
    private final HashMap<String , Rule> rules;

    public GetRulesServerRes(String receiverId, boolean isAccepted,
                             String message, HashMap<String, Rule> rules) {
        super(receiverId, isAccepted, message);
        this.rules = rules;
    }

    public HashMap<String, Rule> getRules() {
        return rules;
    }
}
