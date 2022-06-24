package messenger.service.model.response.server;

import messenger.service.model.response.GetInfoRes;
import messenger.service.model.server.Rule;

import java.util.HashMap;

public class GetRulesServerRes extends GetInfoRes
{
    private HashMap<String , Rule> rules;
}
