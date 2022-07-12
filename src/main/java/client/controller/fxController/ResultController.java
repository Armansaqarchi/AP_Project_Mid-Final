package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController extends Controller{

    @FXML
    private Button ok;

    @FXML
    private Label resLabel;

    @FXML
    private Label resTitle;


    @FXML
    void onOk(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }

    public void setResLabel(String resMes){
        resLabel.setText(resMes);
    }

    public void setResTitle(String resMes){
        resTitle.setText(resMes);
    }
}
