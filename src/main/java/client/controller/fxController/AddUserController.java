package client.controller.fxController;

import client.controller.fxController.type.AddUserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.Channel.AddUserChannelReq;
import model.request.Channel.CreateChannelReq;
import model.request.Channel.RemoveUserChannelReq;
import model.request.server.AddUserServerReq;
import model.response.Response;
import model.server.ChannelType;

public class AddUserController extends Controller
{
    private String serverId;
    private String channelName;

    private AddUserType type;

    @FXML
    private GridPane pane;

    @FXML
    private Label topLabel;

    @FXML
    private TextField userId;

    @FXML
    private Label message;

    @FXML
    private Button done;
    @FXML
    private Button cancel;


    @FXML
    private void done(ActionEvent event)
    {
        switch (type)
        {
            case ADD_SERVER -> clientSocket.send(new AddUserServerReq(clientSocket.getId() , serverId , userId.getText()));
            case ADD_CHANNEL -> clientSocket.send(new AddUserChannelReq(clientSocket.getId() , serverId , channelName , userId.getText()));
            case REMOVE_CHANNEL -> clientSocket.send(new RemoveUserChannelReq(clientSocket.getId() , serverId , channelName , userId.getText()));
        }

        try
        {
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
            setMessage("failed! response not found.");
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

    public void initialize(AddUserType type , String serverId , String channelName)
    {
        this.type = type;
        this.serverId = serverId;
        this.channelName = channelName;

        if(AddUserType.REMOVE_CHANNEL == type)
        {
            topLabel.setText("REMOVE USER");
        }
    }
}
