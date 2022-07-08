package client.controller.fxController;

import client.controller.fxController.cell.ImageTextCell;
import client.controller.fxController.cell.ImageCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import javafx.event.ActionEvent;
import model.exception.ResponseNotFoundException;
import model.request.user.GetFriendListReq;
import model.response.Response;
import model.response.user.GetFriendListRes;

import java.util.ArrayList;
import java.util.HashSet;


public class HomeController extends Controller {


    @FXML
    private ListView<String> serverStatusView;

    @FXML
    private ListView<String> serverView;

    @FXML
    private HBox friendStatusView;

    @FXML
    private ListView<String> friendView;

    @FXML
    private StackPane mainView;

    @FXML
    public void initialize(){
        ArrayList<String> friends = getFriendsId();
        ArrayList<String> servers = new ArrayList<>();
        //the part that takes all the friends images and details

        ObservableList<String> friendObservableList = FXCollections.observableArrayList();
        ObservableList<String> serverObservableList = FXCollections.observableArrayList();



        friendObservableList.add("Friends");
        friendObservableList.add("DIRECT MESSAGES");
        friendObservableList.addAll(friends);

        friendView.setItems(friendObservableList);

        serverObservableList.addAll(servers);
        serverView.setItems(FXCollections.observableArrayList(servers));

        friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new ImageTextCell(clientSocket);
            }
        });

        serverView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new ImageCell();
            }
        });
    }

    public void setClientSocket(String id){
        clientSocket.setId(id);
    }

    @FXML
    public void onServerItem(ActionEvent event){

    }

    @FXML
    public void onFriendItem(ActionEvent event){

    }

    @FXML
    public void onPending(ActionEvent event) {

    }

    @FXML
    public void onAll(ActionEvent event){

    }

    @FXML
    void onAddFriend(ActionEvent event) {

    }

    @FXML
    public void onBlocked(ActionEvent event){

    }


    @FXML
    public void onServerItem(MouseEvent mouseEvent) {
    }

    @FXML
    public void onFriendItem(MouseEvent mouseEvent) {
    }


    public void onOnline(ActionEvent event) {
    }

    public ArrayList<String> getFriendsId(){
        clientSocket.send(new GetFriendListReq(clientSocket.getId()));
        try {
            Response response = clientSocket.getReceiver().getResponse();
            if(response instanceof GetFriendListRes && response.isAccepted()){
                HashSet<String> set = (HashSet<String>)
                        ((GetFriendListRes) response).getFriendList().keySet();
                return new ArrayList<>(set);
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
            System.out.println("no response was receiver from server");
        }

        return new ArrayList<>();

    }


}
