package client.controller.fxController;

import client.ClientSocket;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

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

        friendView.setItems(FXCollections.observableArrayList(friends));
        serverView.setItems(FXCollections.observableArrayList(servers));

        friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return null;
            }
        });
    }




}
