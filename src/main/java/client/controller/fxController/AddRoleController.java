package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class AddRoleController
{
    private String serverId;

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

    }

    @FXML
    private void cancel(ActionEvent event)
    {

    }
}
