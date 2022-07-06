package client.controller.fxController;

import client.ClientSocket;
import client.controller.fxController.cell.FriendCell;
import client.controller.fxController.cell.ServerCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class HomeController extends Controller {

    public HomeController(ClientSocket clientSocket) {
        super(clientSocket);
    }

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
        ArrayList<String> friends = new ArrayList<>();
        ArrayList<String> servers = new ArrayList<>();
        //the part that takes all the friends images and details

        ObservableList<String> friendObservableList = FXCollections.observableArrayList();
        ObservableList<String> serverObservableList = FXCollections.observableArrayList();



        friendObservableList.add("Friends");
        friendObservableList.addAll(friends);

        friendView.setItems(friendObservableList);

        serverObservableList.addAll(servers);
        serverView.setItems(FXCollections.observableArrayList(servers));

        friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new FriendCell();
            }
        });

        serverView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new ServerCell();
            }
        });
    }

    @FXML
    public void onServerItem(ActionEvent event){

    }

    @FXML
    public void onFriendItem(ActionEvent event){

    }

    @FXML
    public void onOnline(ActionEvent event) {

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



}
