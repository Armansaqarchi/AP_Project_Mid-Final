package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.exception.ResponseNotFoundException;
import model.request.Channel.DeleteChannelReq;
import model.request.server.GetRulesServerReq;
import model.request.server.GetServerInfoReq;
import model.response.Response;
import model.response.server.GetRulesServerRes;
import model.response.server.GetServerInfoRes;
import model.server.Rule;
import model.server.RuleType;

import java.io.IOException;

public class EditChannelController extends Controller
{
    private String serverId;
    private String channelName;

    private Stage stage;

    @FXML
    private GridPane pane;

    @FXML
    private Button escape;
    @FXML
    private Button addUser;
    @FXML
    private Button rename;
    @FXML
    private Button removeUser;
    @FXML
    private Button delete;

    @FXML
    private void addUser(ActionEvent event)
    {
        //incomplete
    }

    @FXML
    private void rename(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/renameChannel.fxml"));

        Parent parent = loader.load();

        stage.setScene(new Scene(parent));

        RenameChannelController controller = loader.getController();
        controller.initialize(serverId , channelName);

        stage.show();
    }

    @FXML
    private void removeUser(ActionEvent event)
    {

        //incomplete
    }

    @FXML
    private void delete(ActionEvent event)
    {
        clientSocket.send(new DeleteChannelReq(clientSocket.getId(), serverId, channelName));

        try
        {
            Response response = clientSocket.getReceiver().getResponse();
        }
        catch(ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        closeScene();
    }
    @FXML
    private void escape(ActionEvent event)
    {
        closeScene();
    }

    public void initialize(String serverId , String channelName)
    {
        this.serverId = serverId;
        this.channelName = channelName;

        stage = new Stage();

        stage.focusedProperty().addListener((obs , oldFocus , newFocus) -> focusHandler(newFocus , stage));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);

        //show edit role button for owner of server
        try
        {
            clientSocket.send(new GetServerInfoReq(clientSocket.getId(), serverId));

            GetServerInfoRes response = (GetServerInfoRes) clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                //close the scene if getting roles failed
                closeScene();
            }

            if(response.getOwnerId().equals(clientSocket.getId()))
            {
                rename.setVisible(true);
                removeUser.setVisible(true);
                delete.setVisible(true);
                return;
            }
        }
        catch (ResponseNotFoundException e)
        {
            //close the scene if getting roles failed
            System.out.println(e.getMessage());
            closeScene();
        }

        try
        {
            clientSocket.send(new GetRulesServerReq(clientSocket.getId(), serverId));

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

            if(rule.getRules().contains(RuleType.RENAME_CHANNEL))
            {
                rename.setVisible(true);
            }

            if(rule.getRules().contains(RuleType.RESTRICT_MEMBER))
            {
                removeUser.setVisible(true);
            }

            if(rule.getRules().contains(RuleType.DELETE_CHANNEL))
            {
                delete.setVisible(true);
            }

        }
        catch (ResponseNotFoundException e)
        {
            //close the scene if getting roles failed
            System.out.println(e.getMessage());
            closeScene();
        }
    }

    private void focusHandler(boolean isFocused , Stage stage)
    {
        if(!isFocused)
        {
            stage.close();
        }
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }
}
