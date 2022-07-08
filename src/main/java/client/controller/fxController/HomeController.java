package client.controller.fxController;

import client.ClientSocket;
import client.controller.fxController.cell.ChatCell;
import client.controller.fxController.cell.ImageTextCell;
import client.controller.fxController.cell.ImageCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import javafx.event.ActionEvent;
import model.exception.ResponseNotFoundException;
import model.message.Message;
import model.message.MessageType;
import model.message.TextMessage;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.request.user.GetFriendListReq;
import model.request.user.GetPrivateChatsReq;
import model.response.Response;
import model.response.privateChat.GetPrivateChatHisRes;
import model.response.user.GetFriendListRes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class HomeController extends Controller {


    private String fieldId;

    @FXML
    private ListView<String> serverStatusView;


    @FXML
    private HBox chatHBox;

    @FXML
    private Button FileChooser;

    @FXML
    private ListView<Message> chatListView;

    @FXML
    private TextField chatField;

    @FXML
    private ListView<String> serverView;

    @FXML
    private HBox friendStatusView;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<String> friendView;

    @FXML
    private StackPane mainView;

    @FXML
    public void initialize(){

        chatHBox.setVisible(false);

        ArrayList<String> friends = getFriendsId();
        ArrayList<String> servers = new ArrayList<>();
        ArrayList<Message> chats = new ArrayList<>();
        //the part that takes all the friends images and details

        ObservableList<String> friendObservableList = FXCollections.observableArrayList();
        ObservableList<String> serverObservableList = FXCollections.observableArrayList();
        ObservableList<Message> chatObservableList = FXCollections.observableArrayList();



        friendObservableList.add("Friends");
        friendObservableList.add("DIRECT MESSAGES");
        friendObservableList.addAll(friends);

        friendView.setItems(friendObservableList);

        serverObservableList.addAll(servers);
        serverView.setItems(FXCollections.observableArrayList(servers));

        friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new ImageTextCell();
            }
        });
        serverView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new ImageCell();
            }
        });


        friendView.getSelectionModel().selectedItemProperty()
                .addListener((list, oldValue, newValue) -> friendHandler(newValue));
    }

    @FXML
    public void onServerItem(ActionEvent event){

    }

    @FXML
    public void onFriendItem(ActionEvent event){

    }

    @FXML
    void onSendButton(ActionEvent event) {
        if(chatField.getText().equals("")){
            return;
        }
        clientSocket.send(new TextMessage(null, clientSocket.getId(), fieldId, MessageType.PRIVATE_CHAT,
                LocalDateTime.now(), chatField.getText()));
        chatField.setText("");
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
    void onFileChooser(ActionEvent event) {

    }

    @FXML
    public void onServerItem(MouseEvent mouseEvent) {
    }

    @FXML
    public void onFriendItem(MouseEvent mouseEvent) {
    }

    @FXML
    public void onOnline(ActionEvent event) {
    }

    public void friendHandler(String newValue){
        fieldId = newValue;

        if(newValue.equals("DIRECT MESSAGES")){
            chatListView.setItems(null);
            return;
        }
        else if(newValue.equals("Friends")){
            chatListView.setItems(null);
            return;
        }

        System.out.println(newValue);
        chatListView.setItems(getChatMessages(newValue));



        chatField.setPromptText("Message " + newValue);

        chatListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new ChatCell();
            }
        });

        chatListView.setItems(getChatMessages(newValue));

        chatHBox.setVisible(true);


    }

    public ArrayList<String> getFriendsId(){

        clientSocket.send(new GetFriendListReq(clientSocket.getId()));
        try {
            Response response = clientSocket.getReceiver().getResponse();
            if(response instanceof GetFriendListRes && response.isAccepted())
            {
                return new ArrayList<>(((GetFriendListRes) response).getFriendList().keySet());
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
            System.out.println("no response was receiver from server");
        }

        return new ArrayList<>();

    }

    public ObservableList<Message> getChatMessages(String id){
        clientSocket.send(new GetPrivateChatHisReq(clientSocket.getId(), id));
        try{
            Response response = clientSocket.getReceiver().getResponse();
            if(response.isAccepted()){
                LinkedList<Message> messages = ((GetPrivateChatHisRes)response).getMessages();
                return FXCollections.observableArrayList(new ArrayList<>(messages));
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }


}
