package client.controller.fxController;

import client.Client;
import client.ClientSocket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.response.Response;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;

import java.io.IOException;
import java.util.concurrent.Executors;


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
    void onLogin(Event event) {
        clientSocket.setId(IDText.getText());

        System.out.println(clientSocket);

        clientSocket.send(new LoginReq(clientSocket.getId(), clientSocket.getId(),
                passwordText.getText(), null));

        try{

            Response response = clientSocket.getReceiver().getResponse();


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

    @FXML
    void onKeyLogin(KeyEvent event) {
        System.out.println("dsgfads gha gha");
        if(event.getCode() == KeyCode.ENTER){
            onLogin(event);
        }
    }

    private void moveToHomeScreen(Event event){
        changeView("Home", event);
    }

    private void moveToSignUpScreen(ActionEvent e){
        changeView("SignUp", e);
    }



}
