package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.exception.ResponseNotFoundException;
import model.request.user.GetUserProfileReq;
import model.response.Response;

public class SearchFriendCtr extends Controller{
    @FXML
    private Button chat;

    @FXML
    private Button exit;

    @FXML
    private TextField searchText;

    @FXML
    void onChat(ActionEvent event) {
        clientSocket.send(new GetUserProfileReq(clientSocket.getId(), searchText.getText()));
        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted()){
                FXMLLoader loader = newStageMaker("userProfile");

                ((UserProfileController)loader.getController()).initialize(searchText.getText());
            }
            else if(response.getMessage().equals("you are blocked by this user!")){
                resultMaker("The user has blocked you", "ACCESS DENIED");
            }
            else{
                resultMaker("no user found with the given id : " + searchText.getText(), "INVALID ID");
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
