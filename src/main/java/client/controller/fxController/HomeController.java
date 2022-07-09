package client.controller.fxController;

import client.ClientSocket;
import client.controller.fxController.cell.ChatCell;
import client.controller.fxController.cell.ImageTextCell;
import client.controller.fxController.cell.ImageCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javafx.event.ActionEvent;
import model.exception.ResponseNotFoundException;
import model.message.FileMessage;
import model.message.Message;
import model.message.MessageType;
import model.message.TextMessage;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.request.user.GetFriendListReq;
import model.request.user.GetPrivateChatsReq;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.privateChat.GetPrivateChatHisRes;
import model.response.user.GetFriendListRes;
import model.response.user.GetUserProfileRes;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;


public class HomeController extends Controller {


    private String fieldId;

    private File file;

    private ObservableList<String> friendObservableList = FXCollections.observableArrayList();
    private ObservableList<String> serverObservableList = FXCollections.observableArrayList();
    private ObservableList<Message> chatObservableList = FXCollections.observableArrayList();

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
    private Button cancel;

    @FXML
    private ListView<String> friendView;

    @FXML
    private StackPane mainView;

    @FXML
    public void initialize(){

        chatHBox.setVisible(false);
        cancel.setVisible(false);

        ArrayList<String> friends = getFriendsId();
        ArrayList<String> servers = new ArrayList<>();
        ArrayList<Message> chats = new ArrayList<>();
        //the part that takes all the friends images and details






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

        chatListView.getSelectionModel().selectedItemProperty()
                .addListener((list, oldValue, newValue) -> chatHandler(newValue));
    }

    @FXML
    public void onServerItem(ActionEvent event){

    }

    @FXML
    public void onFriendItem(ActionEvent event){

    }

    @FXML
    void onSendButton(ActionEvent event) {
        Message message = null;
        if(!cancel.isVisible()){
            if(chatField.getText().equals("")){
                return;
            }
            message = new TextMessage(null, clientSocket.getId(), fieldId, MessageType.PRIVATE_CHAT,
                    LocalDateTime.now(), chatField.getText());

            clientSocket.send(message);
            chatField.setText("");
        }
        else{
            try {
                FileInputStream fl = new FileInputStream(file);
                byte[] arr = new byte[(int) file.length()];
                fl.read(arr);
                message = new FileMessage(null, clientSocket.getId(), fieldId,
                        MessageType.PRIVATE_CHAT, LocalDateTime.now(), file.getName(),  arr);
                clientSocket.send(message);
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }

        realTimeUpdate(message);

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
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onFileChooser(ActionEvent event) {
        final FileChooser fc = new FileChooser();

        file = fc.showOpenDialog(new Stage());

        if(file == null){
            return;
        }
        chatField.setDisable(true);
        cancel.setVisible(true);

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

        imageSever(newValue);

        if(newValue.equals("DIRECT MESSAGES")){
            chatListView.setItems(null);
            chatHBox.setVisible(false);
            return;
        }
        else if(newValue.equals("Friends")){
            chatListView.setItems(null);
            chatHBox.setVisible(false);
            return;
        }

        chatField.setDisable(false);
        chatHBox.setVisible(true);
        cancel.setVisible(false);

        System.out.println(newValue);

        chatObservableList = getChatMessages(newValue);

        chatListView.setItems(chatObservableList);



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

            if(response.isAccepted() && response instanceof GetPrivateChatHisRes){
                LinkedList<Message> messages = ((GetPrivateChatHisRes)response).getMessages();
                return FXCollections.observableArrayList(new ArrayList<>(messages));
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }


    public void realTimeUpdate(Message message){

        if(fieldId.equals(message.getSenderId()) || clientSocket.getId().equals(message.getSenderId())) {

            chatObservableList.add(message);
            chatListView.setItems(chatObservableList);
        }
    }

    private void imageSever(String id){
        byte[] file = getImageById(id);


        Path path = Paths.get("resource/image/friends/" + id);

        try {
            if (file == null) {
                Files.deleteIfExists(path);
                return;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            Files.createFile(path);
        }
        catch(FileAlreadyExistsException e){
            try {
                byte[] existingFile = Files.readAllBytes(path);

                if(!Arrays.equals(file, existingFile)){
                    Files.write(path, file);
                }
            }
            catch(IOException ex){
                e.printStackTrace();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized byte[] getImageById(String id){
        clientSocket.send(new GetUserProfileReq(clientSocket.getId(), id));
        try{
            Response response = clientSocket.getReceiver().getResponse();
            if (response instanceof GetUserProfileRes) {

                if(response.isAccepted()) {
                    return ((GetUserProfileRes) response).getProfileImage();
                }
                else{
                    System.out.println("access denied to get " + id + "'s image");
                }
            }


        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();

            System.out.println("no response was receiver from server");;
        }

        return null;
    }

    public void chatHandler(Message newValue){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modify.fxml"));
            Stage modifyStage = new Stage();

            Point p = MouseInfo.getPointerInfo().getLocation();

            modifyStage.setX(p.getX());
            modifyStage.setY(p.getY());

            Scene scene = new Scene(loader.load());

            modifyStage.setScene(scene);

            ModifyController controller = loader.getController();

            controller.setMessage(newValue);

            modifyStage.initStyle(StageStyle.UNDECORATED);
            modifyStage.initStyle(StageStyle.TRANSPARENT);
            modifyStage.setResizable(false);


            modifyStage.focusedProperty().addListener((obs, oldFocus, newFocus) -> focusHandler(newFocus, modifyStage));


            scene.setFill(Color.TRANSPARENT);


            modifyStage.show();
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("cant load modify window");
        }
    }

    private void focusHandler(boolean isFocused, Stage stage){
        if(!isFocused){
            stage.close();
        }
    }




}
