package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NoReactionController extends Controller {

    @FXML
    private Button ok;

    @FXML
    void onOk(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
}
