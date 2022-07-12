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
import model.request.user.AnswerFriendReq;
import model.request.user.GetBlockedUsersReq;
import model.request.user.GetFriendReqList;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.user.GetBlockedUsersRes;
import model.response.user.GetFriendReqListRes;
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
        changeView("Home", event);
    }



    public void getOnlineFriends(){

        ArrayList<String> allFriends = (ArrayList<String>) getIds("friends");
        ArrayList<String> onlineFriends = new ArrayList<>();

        for(String i : allFriends){
            try{

                clientSocket.send(new GetUserProfileReq(clientSocket.getId(), i));
                Response response = clientSocket.getReceiver().getResponse();
                if(response.isAccepted()){

                    if(((GetUserProfileRes)response).getUserStatus() == UserStatus.ONLINE) {
                        imageSaver(((GetUserProfileRes) response).getId(), "friends",
                                ((GetUserProfileRes) response).getProfileImage());

                        onlineFriends.add(i);
                    }
                }
            }
            catch(ResponseNotFoundException e){
                e.printStackTrace();
            }
        }

        statusListView.setItems(FXCollections.observableArrayList(onlineFriends));

    }

    public ObservableList<String> getBlockedFriends(){


        clientSocket.send(new GetBlockedUsersReq(clientSocket.getId()));

        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted() && response instanceof GetBlockedUsersRes){

                ObservableList<String> obs = FXCollections.observableList
                        (new ArrayList<>(((GetBlockedUsersRes) response).getBlockedUsers()));

                statusListView.setItems(obs);

                return obs;
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }


        return null;
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

        statusListView.setItems(list);
    }




}
