package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.user.SetMyProfileReq;
import model.response.Response;
import model.user.UserStatus;

public class SetStatusController extends Controller
{
    @FXML
    private GridPane pane;

    @FXML
    private Button online;
    @FXML
    private Button idle;
    @FXML
    private Button disturb;
    @FXML
    private Button invisible;

    @FXML
    private void eventHandler(ActionEvent event)
    {
        UserStatus status = null;

        if(event.getSource().equals(online))
        {
            status = UserStatus.ONLINE;
        }
        else if(event.getSource().equals(idle))
        {
            status = UserStatus.IDLE;
        }
        else if(event.getSource().equals(disturb))
        {
            status = UserStatus.DO_NOT_DISTURB;
        }
        else if(event.getSource().equals(invisible))
        {
            status = UserStatus.INVISIBLE;
        }

        try
        {
            clientSocket.send(new SetMyProfileReq(clientSocket.getId() , null , null
                    , null , null ,
                    null , null , status));

            Response response = clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                return;
            }

            closeScene();
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.hide();
    }
}
