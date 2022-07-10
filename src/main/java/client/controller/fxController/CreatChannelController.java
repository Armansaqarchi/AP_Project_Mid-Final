package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.Channel.CreateChannelReq;
import model.response.Response;
import model.server.ChannelType;


public class CreatChannelController extends Controller
{
    private String serverId;

    @FXML
    private GridPane pane;

    @FXML
    private Button done;
    @FXML
    private Button cancel;

    @FXML
    private TextField channelName;

    @FXML
    private Label message;


    @FXML
    private void done(ActionEvent event)
    {
        try
        {
            clientSocket.send(new CreateChannelReq(clientSocket.getId(), serverId,
                    channelName.getText(), ChannelType.TEXT));

            Response response = clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                setMessage(response.getMessage());
                return;
            }

            closeScene();
        }
        catch(ResponseNotFoundException e)
        {
            setMessage("Creating channel failed!");
        }
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        closeScene();
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }

    private void setMessage(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }
}
