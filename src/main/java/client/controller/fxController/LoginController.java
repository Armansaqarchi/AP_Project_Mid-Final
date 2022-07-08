package client.controller.fxController;

import client.Client;
import client.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;



import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.response.Response;





public class LoginController extends Controller {

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

        System.out.println(clientSocket);

        clientSocket.send(new LoginReq(clientSocket.getId(), clientSocket.getId(),
                passwordText.getText(), null));

        try{
            System.out.println("1 is here");
            Response response = clientSocket.getReceiver().getResponse();
            System.out.println("2");

            if(response.isAccepted()){
                moveToHomeScreen(event);
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

    private void moveToHomeScreen(ActionEvent event){
        changeView("Home", event);
    }

    private void moveToSignUpScreen(ActionEvent e){
        changeView("SignUp", e);
    }



}
