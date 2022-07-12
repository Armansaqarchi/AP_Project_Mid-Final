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
import model.request.Channel.RenameChannelReq;
import model.response.Response;
import model.server.ChannelType;

public class RenameChannelController extends Controller
{
    private String serverId;
    private String channelName;

    @FXML
    private GridPane pane;

    @FXML
    private TextField newName;

    @FXML
    private Button done;

    @FXML
    private Button cancel;

    @FXML
    private Label message;

    @FXML
    private void done(ActionEvent event)
    {
        try
        {
            clientSocket.send(new RenameChannelReq(clientSocket.getId(), serverId,
                    channelName , newName.getText()));

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
            System.out.println(e.getMessage());
            setMessage("Renaming channel failed!");
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

    public void initialize(String serverId , String channelName)
    {
        this.serverId = serverId;
        this.channelName = channelName;
    }
}
