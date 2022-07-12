package client.controller.fxController;

import client.controller.fxController.cell.PendingCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.exception.ResponseNotFoundException;
import model.request.user.AnswerFriendReq;
import model.request.user.GetFriendReqList;
import model.response.Response;
import model.response.user.GetFriendReqListRes;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class PendingController extends Controller {

    public void initialize(){
        pendingView.setCellFactory(new Callback<ListView<Map.Entry<UUID, String>>, ListCell<Map.Entry<UUID, String>>>() {
            @Override
            public ListCell<Map.Entry<UUID, String>> call(ListView<Map.Entry<UUID, String>> entryListView) {
                return new PendingCell();
            }
        });

        pendingView.getSelectionModel().selectedItemProperty().
                addListener((obs, oldValue, newValue) -> friendReqHandler(newValue));
    }

    @FXML
    private Button exit;

    @FXML
    private ListView<Map.Entry<UUID, String>> pendingView;

    ObservableList<Map.Entry<UUID, String>> friendReqList = FXCollections.observableArrayList();

    @FXML
    void onExit(ActionEvent event) {
        changeView("Home", event);
    }

    private void friendReqHandler(Map.Entry<UUID, String> newValue){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnswerFriendReq.fxml"));

            Stage modifyStage = new Stage();

            Point p = MouseInfo.getPointerInfo().getLocation();

            modifyStage.setX(p.getX());
            modifyStage.setY(p.getY());

            Scene scene = new Scene(loader.load());

            modifyStage.setScene(scene);

            AnswerFriendReqController controller = loader.getController();
            controller.setSelectedFriendReq(newValue.getKey());

            modifyStage.initStyle(StageStyle.UNDECORATED);
            modifyStage.initStyle(StageStyle.TRANSPARENT);
            modifyStage.setResizable(false);


            scene.setFill(Color.TRANSPARENT);


            modifyStage.show();


        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("cant load AnswerFriendReq window");
        }
    }

    public void getPendingFriendsReq(){
        clientSocket.send(new GetFriendReqList(clientSocket.getId()));

        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted() && response instanceof GetFriendReqListRes){



                friendReqList.addAll(new ArrayList<>(((GetFriendReqListRes) response)
                        .getFriendRequests().entrySet()));


                pendingView.setItems(friendReqList);


            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }
    }

    public ListView<Map.Entry<UUID, String>> getPendingView() {
        return pendingView;
    }

    public void setPendingView(ListView<Map.Entry<UUID, String>> pendingView) {
        this.pendingView = pendingView;
    }

    public ObservableList<Map.Entry<UUID, String>> getFriendReqList() {
        return friendReqList;
    }

    public void setFriendReqList(ObservableList<Map.Entry<UUID, String>> friendReqList) {
        this.friendReqList = friendReqList;
    }
}
