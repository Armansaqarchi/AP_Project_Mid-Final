package client.controller.fxController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;


public class MyProfileController extends Controller implements Initializable
{
    @FXML
    private Button escape;

    @FXML
    private Button editId;
    @FXML
    private Button editName;
    @FXML
    private Button editEmail;
    @FXML
    private Button editPhoneNumber;

    @FXML
    private Button editImage;
    @FXML
    private Button changePassword;
    @FXML
    private Button logout;

    @FXML
    private Label userId;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;

    @FXML
    private Circle image;
    @FXML
    private Circle status;

    @FXML
    private void eventHandler(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Image image = new Image("image/user-default.png");
        ImagePattern pattern = new ImagePattern(image);
        this.image.setFill(pattern);

        Image status = new Image("image/idle.png");
        ImagePattern status_pattern = new ImagePattern(status);
        this.status.setFill(status_pattern);
    }
}
