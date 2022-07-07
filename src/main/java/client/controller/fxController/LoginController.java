package client.controller.fxController;

import client.Client;
import client.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.response.Response;





public class LoginController extends Controller {

    private ClientSocket clientSocket;

    @FXML
    private ImageView image;

    @FXML
    private Label errorText;

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
        clientSocket.setId(IDText.getText());

        clientSocket.send(new LoginReq(clientSocket.getId(), clientSocket.getId(),
                passwordText.getText(), null));

        try{

            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted()){
                moveToHomeScreen(clientSocket.getId(), event);
            }
            else{
                errorText.setVisible(true);
                errorText.setText("No user found with the given information");
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
            errorText.setText("No response was received from server");
        }
    }

    @FXML
    void onRegister(ActionEvent event) {
        moveToSignUpScreen(event);
    }

    private void moveToHomeScreen(String id, ActionEvent event){
        HomeController controller = changeView("Home", event).getController();
        if(controller != null) {
            controller.setClientSocket(id);
        }
    }

    private void moveToSignUpScreen(ActionEvent e){
        SignUpController controller =  changeView("SignUp", e).getController();
        controller.setClientSocket(clientSocket);
    }

    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket = clientSocket;
    }



}
