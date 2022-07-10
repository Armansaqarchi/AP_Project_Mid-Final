package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FailedFriendReqCtr
{
    @FXML
    private GridPane pane;

    @FXML
    private Button okay;

    @FXML
    private void okay(ActionEvent event)
    {
        closeScene();
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }
}
