package client.controller.fxController;

import client.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SignUpController extends Controller {

    public SignUpController(ClientSocket clientSocket) {
        super(clientSocket);
    }

    @FXML
    private TextField IDText;

    @FXML
    private TextField emailText1;

    @FXML
    private Button haveAnAccount;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    void onHaveAnAccount(ActionEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {

    }

}
