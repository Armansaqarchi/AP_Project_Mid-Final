package client.controller.fxController;

import client.controller.fxController.cell.StatusCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.exception.ResponseNotFoundException;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.user.GetUserProfileRes;
import model.user.UserStatus;

import java.util.ArrayList;

public class StatusViewController extends Controller {

    public void initialize(){
        statusListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new StatusCell();
            }
        });
    }

    @FXML
    private ListView<String> statusListView;

    @FXML
    private Button exit;

    @FXML
    void onExit(ActionEvent event) {
        FXMLLoader loader = changeView("Home", event);
    }



    public void getOnlineFriends(){

        ArrayList<String> allFriends = (ArrayList<String>) getIds("friends");
        ArrayList<String> onlineFriends = new ArrayList<>();

        for(String i : allFriends){
            try{

                clientSocket.send(new GetUserProfileReq(clientSocket.getId(), i));
                Response response = clientSocket.getReceiver().getResponse();
                if(response.isAccepted()){

                    if(((GetUserProfileRes)response).getUserStatus() == UserStatus.ONLINE)
                        imageSaver(((GetUserProfileRes) response).getId(), "friends",
                                ((GetUserProfileRes) response).getProfileImage());

                        onlineFriends.add(i);
                }
            }
            catch(ResponseNotFoundException e){
                e.printStackTrace();
            }
        }

        statusListView.setItems(FXCollections.observableArrayList(onlineFriends));

    }

    public void getAllFriends(){
        ArrayList<String> allFriends = (ArrayList<String>) getIds("friends");

        for(String i : allFriends){
            try{
                clientSocket.send(new GetUserProfileReq(clientSocket.getId(), i));
                Response response = clientSocket.getReceiver().getResponse();

                if(response.isAccepted()){
                    imageSaver(((GetUserProfileRes) response).getId(), "friends",
                        ((GetUserProfileRes) response).getProfileImage());
                }
            }
            catch(ResponseNotFoundException e){
                e.printStackTrace();
            }
        }

        ObservableList<String> list = FXCollections.observableArrayList(allFriends);
        list.addAll(allFriends);
        list.addAll(allFriends);
        list.addAll(allFriends);

        statusListView.setItems(list);
    }


}
