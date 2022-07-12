package client.controller.fxController;

import client.controller.InfoVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.exception.InvalidUsernameException;
import model.exception.ResponseNotFoundException;
import model.request.user.FriendReq;
import model.response.Response;

import java.awt.*;
import java.io.IOException;

public class AddFriendController extends Controller {

    @FXML
    private Button addButton;

    @FXML
    private TextField addText;

    @FXML
    private Button exit;

    @FXML

    void onAdd(ActionEvent event) {

        try {
            InfoVerifier.checkUserValidity(addText.getText());
        }
        catch (InvalidUsernameException e){
            resultMaker("ID must have 6 characters at least containing a number and an uppercase", "INVALID FORMAT");
        }



        clientSocket.send(new FriendReq(clientSocket.getId(), null, addText.getText()));

        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted()) {

                resultMaker(response.getMessage(), "FRIEND REQUEST SENT");

            }
            else{
                resultMaker("Hm, didn't work. Double check that the capitalization," +
                        " spelling, any spaces, and numbers are correct", "FRIEND REQUEST FAILED");
            }

        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onExit(ActionEvent event) {
        changeView("Home", event);
    }
}
