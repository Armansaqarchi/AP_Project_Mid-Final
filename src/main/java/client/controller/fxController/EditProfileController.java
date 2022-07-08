package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController extends Controller implements Initializable
{
    @FXML
    private Label topLabel;

    @FXML
    private Label editValue;

    @FXML
    private Label responseMessage;

    @FXML
    private Button cancel;
    @FXML
    private Button done;

    @FXML
    private void eventHandler(ActionEvent event)
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        topLabel.setText("dddd");
    }
}
