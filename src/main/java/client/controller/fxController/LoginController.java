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
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.Authentication.LoginReq;
import model.response.Response;

import javax.swing.text.html.ImageView;
import java.io.IOException;


public class LoginController {

    private final ClientSocket clientSocket = new ClientSocket();

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

            }
            else{
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

    }

    private void moveToHomeScreen(String id, ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Home.fxml"));
            Parent homeParent = loader.load();

            HomeController controller = loader.getController();
            controller.setClientSocket(id);

            Stage window = (Stage) ((Button)event.getSource()).getScene().getWindow();

            window.setScene(new Scene(homeParent));

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("could not load Home.fxml class");
        }
    }



}
