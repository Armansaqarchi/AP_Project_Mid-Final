package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;



public class MyProfileController
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
    private TextField userId;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;

    @FXML
    private Circle image;
    @FXML
    private Circle status;

    @FXML
    private void eventHandler(ActionEvent event) {

    }
}
