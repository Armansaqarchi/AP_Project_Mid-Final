package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.server.AddRuleServerReq;
import model.request.server.GetRulesServerReq;
import model.response.Response;
import model.response.server.GetRulesServerRes;
import model.server.Rule;
import model.server.RuleType;

public class EditRoleController extends Controller
{
    private String serverId;
    private String userId;

    @FXML
    private GridPane pane;
    @FXML
    private Button done;
    @FXML
    private Button cancel;


    @FXML
    private CheckBox createChannel;

    @FXML
    private CheckBox deleteChannel;

    @FXML
    private CheckBox renameChannel;

    @FXML
    private CheckBox removeMember;

    @FXML
    private CheckBox restrictMember;

    @FXML
    private CheckBox renameServer;

    @FXML
    private CheckBox getHistory;

    @FXML
    private CheckBox pinMessage;

    @FXML
    private CheckBox setImage;

    @FXML
    private void done(ActionEvent event)
    {
        Rule rule = new Rule(userId);

        if(createChannel.isSelected())
        {
            rule.getRules().add(RuleType.CREATE_CHANNEL);
        }
        if(deleteChannel.isSelected())
        {
            rule.getRules().add(RuleType.DELETE_CHANNEL);
        }
        if(renameChannel.isSelected())
        {
            rule.getRules().add(RuleType.RENAME_CHANNEL);
        }
        if(removeMember.isSelected())
        {
            rule.getRules().add(RuleType.REMOVE_MEMBER);
        }
        if(restrictMember.isSelected())
        {
            rule.getRules().add(RuleType.RESTRICT_MEMBER);
        }
        if(renameServer.isSelected())
        {
            rule.getRules().add(RuleType.RENAME_SERVER);
        }
        if(getHistory.isSelected())
        {
            rule.getRules().add(RuleType.WATCH_HISTORY);
        }
        if(pinMessage.isSelected())
        {
            rule.getRules().add(RuleType.PIN_MESSAGE);
        }
        if(setImage.isSelected())
        {
            rule.getRules().add(RuleType.SET_IMAGE);
        }

        try
        {
            clientSocket.send(new AddRuleServerReq(clientSocket.getId(), serverId , rule));

            Response response = clientSocket.getReceiver().getResponse();

            //show responses message
            closeScene();
        }
        catch (ResponseNotFoundException e)
        {
            closeScene();
            //show responses message
        }

    }

    @FXML
    private void cancel(ActionEvent event)
    {
        closeScene();
    }

    public void setId(String serverId , String userId)
    {
        this.serverId = serverId;
        this.userId = userId;

        initialize();
    }

    private void initialize()
    {
        try
        {
            clientSocket.send(new GetRulesServerReq(userId , serverId));

            GetRulesServerRes response = (GetRulesServerRes)clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                //close the scene if getting roles failed
                closeScene();
            }

            Rule rule = response.getRules().get(clientSocket.getId());

            if(null == rule)
            {
                return;
            }

            //set the roles of this user to checked
            for(RuleType ruleType : rule.getRules())
            {
                switch (ruleType)
                {
                    case RENAME_CHANNEL -> renameChannel.setSelected(true);
                    case DELETE_CHANNEL -> deleteChannel.setSelected(true);
                    case PIN_MESSAGE -> pinMessage.setSelected(true);
                    case RENAME_SERVER -> renameServer.setSelected(true);
                    case SET_IMAGE -> setImage.setSelected(true);
                    case REMOVE_MEMBER -> removeMember.setSelected(true);
                    case CREATE_CHANNEL -> createChannel.setSelected(true);
                    case RESTRICT_MEMBER -> restrictMember.setSelected(true);
                    case WATCH_HISTORY -> getHistory.setSelected(true);
                }
            }
        }
        catch (ResponseNotFoundException e)
        {
            //close the scene if getting roles failed
            closeScene();
        }
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }
}
