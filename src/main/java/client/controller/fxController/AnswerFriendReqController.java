package client.controller.fxController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.user.AnswerFriendReq;
import model.response.Response;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class AnswerFriendReqController extends Controller {


    private UUID selectedFriendReq;

    @FXML
    private Button accept;

    @FXML
    private Button ignore;

    @FXML
    void onAccept(ActionEvent event) {
        clientSocket.send(new AnswerFriendReq(clientSocket.getId(), selectedFriendReq, true));

        try{
            Response response = clientSocket.getReceiver().getResponse();
            PendingController controller = clientSocket.getReceiver().getLoader().getController();

            if(response.isAccepted()){
                resultMaker("Request accepted", "ACCEPTED");
            }

            else{
                resultMaker("something is wrong with accepting the request", "SOMETHING IS WRONG");
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        updateReqList();

        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void onIgnore(ActionEvent event) {
        clientSocket.send(new AnswerFriendReq(clientSocket.getId(), selectedFriendReq, false));

        try {
            Response response = clientSocket.getReceiver().getResponse();
            PendingController controller = clientSocket.getReceiver().getLoader().getController();

            if (response.isAccepted()) {

                resultMaker("Request declined", "ACCEPTED");

            } else {
                resultMaker("something is wrong with declining the request","SOMETHING IS WRONG");
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        updateReqList();

        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }


    public void setSelectedFriendReq(UUID friendReq){
        this.selectedFriendReq = friendReq;
    }

    public void updateReqList(){
        FXMLLoader loader = clientSocket.getReceiver().getLoader();

        PendingController controller = loader.getController();
        ObservableList<Map.Entry<UUID, String>> obs = controller.getFriendReqList();
        obs.removeIf(i -> i.getKey().equals(selectedFriendReq));

        controller.getPendingView().setItems(obs);

    }

}
