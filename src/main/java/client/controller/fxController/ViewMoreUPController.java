package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.user.*;
import model.response.Response;
import model.response.user.GetBlockedUsersRes;
import model.response.user.GetFriendListRes;

public class ViewMoreUPController extends Controller
{
    private String userId;

    @FXML
    private GridPane pane;

    @FXML
    private Button friend;
    @FXML
    private Button block;
    @FXML
    private Button sendMessage;

    @FXML
    private void sendMessage(ActionEvent event)
    {
        //incomplete
    }

    @FXML
    private void block(ActionEvent event)
    {
        if(block.getText().equals("unblock"))
        {
            clientSocket.send(new UnBlockUserReq(clientSocket.getId() , userId));
        }
        else
        {
            clientSocket.send(new BlockUserReq(clientSocket.getId() , userId));
        }

        try
        {
            Response response = clientSocket.getReceiver().getResponse();
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        closeScene();
    }

    @FXML
    private void friend(ActionEvent event)
    {
        if(block.getText().equals("remove friend"))
        {
            clientSocket.send(new RemoveFriendReq(clientSocket.getId() , userId));
        }
        else
        {
            clientSocket.send(new FriendReq(clientSocket.getId() , null , userId));
        }

        try
        {
            Response response = clientSocket.getReceiver().getResponse();
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        closeScene();
    }

    public void initialize(String userId)
    {
        this.userId = userId;
        initialize();
    }

    private void initialize()
    {
        try
        {
            clientSocket.send(new GetFriendListReq(clientSocket.getId()));

            GetFriendListRes response = (GetFriendListRes) clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                closeScene();
            }

            if(response.getFriendList().keySet().contains(userId))
            {
                friend.setText("remove friend");
            }
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
            closeScene();
        }

        try
        {
            clientSocket.send(new GetBlockedUsersReq(clientSocket.getId()));

            GetBlockedUsersRes response = (GetBlockedUsersRes) clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                closeScene();
            }

            if(response.getBlockedUsers().contains(userId))
            {
                block.setText("unblock");
            }
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
            closeScene();
        }
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.hide();
    }
}
