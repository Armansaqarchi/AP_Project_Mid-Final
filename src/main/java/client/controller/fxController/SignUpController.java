package client.controller.fxController;

import client.Client;
import client.ClientSocket;
import client.controller.InfoVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.exception.*;
import model.request.Authentication.SignupReq;
import model.response.Response;

import java.io.IOException;


public class SignUpController extends Controller{

    private final ClientSocket clientSocket = new ClientSocket();

    @FXML
    private TextField IDText;

    @FXML
    private Label errorText;

    @FXML
    private TextField emailText;

    @FXML
    private Button haveAnAccount;

    @FXML
    private Button signUp;

    @FXML
    private TextField nameText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    void onHaveAnAccount(ActionEvent event) {
        moveToSignInScreen(event);
    }

    @FXML
    void onSignUp(ActionEvent event) {
        try{
            InfoVerifier.checkUserValidity(IDText.getText());
            InfoVerifier.checkPasswordValidity(passwordText.getText());
            InfoVerifier.checkEmailValidity(emailText.getText());
            InfoVerifier.checkPhoneNumberValidity(phoneNumberText.getText());
        }
        catch(InvalidUsernameException e){
            errorText.setText("user id must have at least 6 characters and only English characters");
            errorText.setVisible(true);
            return;
        }
        catch(InvalidPasswordException e){
            errorText.setText("password must have at least 8 characters containing a number and an upperCase and a loweCase");
            errorText.setVisible(true);
            return;
        }
        catch(InvalidEmailFormatException e){
            errorText.setText("email should have a valid domain like : @gmail.com");
            errorText.setVisible(true);
            return;
        }
        catch(InvalidPhoneNumberException e){
            errorText.setText("phone number must have exactly 11 characters and starts with 09...");
            errorText.setVisible(true);
            return;
        }

        clientSocket.setId(IDText.getText());

        //sends the related req to the server
        clientSocket.send(new SignupReq(clientSocket.getId(), clientSocket.getId(),
                passwordText.getText(), null, nameText.getText(), emailText.getText(),
                phoneNumberText.getText(), null));

        try{
            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                moveToHomeScreen(IDText.getText(), event);
            }
            else{
                errorText.setText("something is wrong with registering, try again.");
                errorText.setVisible(true);
            }
        }
        catch(ResponseNotFoundException e){
            errorText.setVisible(true);
            errorText.setText("No response was received from server");
        }

    }


    private void moveToHomeScreen(String id, ActionEvent event){
        HomeController controller = changeView("Home", event).getController();
        if(controller != null) {
            controller.setClientSocket(id);
        }
    }

    private void moveToSignInScreen(ActionEvent event){
        changeView("Login", event);
    }

}
