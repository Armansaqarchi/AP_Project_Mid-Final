package client.controller.fxController;

import client.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {

    public LoginController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    private TextField IDText;

    @FXML
    private Button loginButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private TextField passwordText;

    @FXML
    void onLogin(ActionEvent event) {

    }

    @FXML
    void onRegister(ActionEvent event) {

    }


}
