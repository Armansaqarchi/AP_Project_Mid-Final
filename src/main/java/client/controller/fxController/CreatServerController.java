package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.server.CreateServerReq;
import model.response.Response;

import javax.swing.*;

public class CreatServerController extends Controller
{
    @FXML
    private GridPane pane;

    @FXML
    private Button create;
    @FXML
    private Button cancel;

    @FXML
    private TextField id;
    @FXML
    private TextField name;

    @FXML
    private Label message;

    @FXML
    private void create(ActionEvent event)
    {
        try
        {
            //sends the related req
            clientSocket.send(new CreateServerReq(clientSocket.getId() , id.getText() , name.getText() , null));

            //takes the response
            Response response = clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                setMessage(response.getMessage());
                return;
            }

            closeScene(event);
        }
        catch(ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
            setMessage("Failed to create server!");
        }
    }

    @FXML
    private void cancel(ActionEvent event)
    {

        closeScene(event);
    }

    private void closeScene(ActionEvent event)
    {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.hide();
    }

    private void setMessage(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }
}
