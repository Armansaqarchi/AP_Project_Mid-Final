package client.controller.fxController;

import client.controller.fxController.cell.*;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javafx.event.ActionEvent;
import model.exception.ResponseNotFoundException;
import model.message.*;
import model.request.Channel.GetChatHistoryReq;
import model.request.priavteChat.GetPrivateChatHisReq;
import model.request.server.GetRulesServerReq;
import model.request.server.GetServerInfoReq;
import model.request.server.GetUsersStatusReq;
import model.request.user.*;
import model.response.Response;
import model.response.channel.GetChatHistoryRes;
import model.response.privateChat.GetPrivateChatHisRes;
import model.response.server.GetRulesServerRes;
import model.response.server.GetServerInfoRes;
import model.response.server.GetUserStatusRes;
import model.response.user.GetFriendListRes;
import model.response.user.GetPrivateChatsRes;
import model.response.user.GetServersRes;
import model.response.user.GetUserProfileRes;
import model.server.Rule;
import model.server.RuleType;
import model.server.Server;
import model.user.ServerIDs;
import model.user.UserStatus;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;


public class HomeController extends Controller {


    public Button addFriend;
    public Button pending;
    public Button all;
    public Button blocked;


    ChangeListener<String> currentListener;

    public ImageView statusView;

    private String fieldId;

    private String serverId;

    private File file;

    private ObservableList<String> friendObservableList = FXCollections.observableArrayList();
    private ObservableList<ServerIDs> serverObservableList = FXCollections.observableArrayList();
    private ObservableList<Message> chatObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<Map.Entry<String, UserStatus>> serverStatusView;

    private ServerIDs serverIDs;

    @FXML
    private HBox chatHBox;

    @FXML
    private Button FileChooser;

    @FXML
    private ListView<Message> chatListView;

    @FXML
    private Label friendName;

    @FXML
    private Button myProfile;

    @FXML
    private Button search;

    @FXML
    private HBox friendStatusView;

    @FXML
    private HBox targetFriendHBox;

    @FXML
    private TextField chatField;

    @FXML
    private ListView<ServerIDs> serverView;

    @FXML
    private Button sendButton;

    @FXML
    private Button cancel;

    @FXML
    Button pinMessages;

    @FXML
    private ListView<String> friendView;

    @FXML
    private StackPane mainView;

    @FXML
    public void initialize(){



        chatHBox.setVisible(false);
        cancel.setVisible(false);


        pinMessages.setVisible(false);

        //the part that takes all the friends images and details

        targetFriendHBox.setVisible(false);

        friendObservableList.clear();
        serverObservableList.clear();

        friendObservableList.add("Friends");
        friendObservableList.add("DIRECT MESSAGES");
        friendObservableList.addAll(getDirectMessages());

        friendView.setItems(friendObservableList);


        serverObservableList.add(new ServerIDs("Home", null));
        serverObservableList.addAll((ArrayList<ServerIDs>)getIds("servers"));
        serverObservableList.add(new ServerIDs("Add server", null));
        serverView.setItems(serverObservableList);

        friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> userListView) {
                return new ImageTextCell();
            }
        });
        serverView.setCellFactory(new Callback<ListView<ServerIDs>, ListCell<ServerIDs>>() {
            @Override
            public ListCell<ServerIDs> call(ListView<ServerIDs> userListView) {
                return new ImageCell();
            }
        });
        chatListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new ChatCell();
            }
        });


        currentListener = (list, oldValue, newValue) -> friendHandler(newValue);

        friendView.getSelectionModel().selectedItemProperty()
                .addListener(currentListener);
        chatListView.getSelectionModel().selectedItemProperty()
                .addListener((list, oldValue, newValue) -> chatHandler(newValue));
        serverView.getSelectionModel().selectedItemProperty()
                .addListener((obs, olaValue, newValue) -> serverHandler(newValue));
        serverStatusView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> serverMemberHandler(newValue));
    }

    public ObservableList<String> getDirectMessages(){
        clientSocket.send(new GetPrivateChatsReq(clientSocket.getId()));

        ObservableList<String> obsList = FXCollections.observableArrayList();

        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted() && response instanceof GetPrivateChatsRes){
                ArrayList<String> privateChats = new ArrayList<>(((GetPrivateChatsRes) response).getPrivateChats());

                for(String i : privateChats){
                    String[] ids = i.split("-");

                    if(ids[0].equals(clientSocket.getId())){

                        i = ids[1];

                    }
                    else{
                        i = ids[0];

                    }

                    obsList.add(i);
                }

                return obsList;
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    @FXML
    void onSendButton(ActionEvent event) {
        chatField.setDisable(false);

        Message message = null;
        if(!cancel.isVisible()){

            if(chatField.getText().equals("")){
                return;
            }

            if(serverId == null) {
                message = new TextMessage(null, clientSocket.getId(), fieldId, MessageType.PRIVATE_CHAT,
                        LocalDateTime.now(), chatField.getText());
            }
            else{
                message = new TextMessage(null, clientSocket.getId(), serverId + "-" + fieldId, MessageType.CHANNEL,
                        LocalDateTime.now(), chatField.getText());
            }

            clientSocket.send(message);
            chatField.setText("");
        }
        else{
            try {
                cancel.setVisible(false);
                FileInputStream fl = new FileInputStream(file);
                byte[] arr = new byte[(int) file.length()];
                fl.read(arr);

                if(serverId == null) {
                    message = new FileMessage(null, clientSocket.getId(), fieldId,
                            MessageType.PRIVATE_CHAT, LocalDateTime.now(), file.getName(), arr);
                }
                else{
                    message = new FileMessage(null, clientSocket.getId(), serverId + "-" + fieldId,
                            MessageType.CHANNEL, LocalDateTime.now(), file.getName(), arr);
                }
                clientSocket.send(message);
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        if(message instanceof FileMessage){
            message = new FileMsgNotification((FileMessage) message);
        }
        realTimeUpdate(message);

    }

    @FXML
    public void onPending(ActionEvent event) {
        FXMLLoader loader = changeView("Pending", event);

        PendingController controller = loader.getController();

        controller.getPendingFriendsReq();
    }

    @FXML
    public void onAll(ActionEvent event){



        FXMLLoader loader = changeView("StatusView", event);

        StatusViewController controller = loader.getController();

        controller.getAllFriends();
    }

    @FXML
    void onAddFriend(ActionEvent event) {
        changeView("AddFriend", event);
    }

    @FXML
    public void onBlocked(ActionEvent event){

        FXMLLoader loader = changeView("StatusView", event);

        StatusViewController controller = loader.getController();

        controller.getBlockedFriends();
    }


    @FXML
    void onCancel(ActionEvent event) {
        cancel.setVisible(false);
        chatField.setDisable(false);

        file = null;

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
    public void onOnline(ActionEvent event) {


        FXMLLoader loader = changeView("StatusView", event);

        StatusViewController controller = loader.getController();

        controller.getOnlineFriends();

    }

    public void friendHandler(String newValue){

        if(newValue == null){
            return;
        }

        serverId = null;

        fieldId = newValue;

        if(newValue.equals("DIRECT MESSAGES") || newValue.equals("Friends")){


            friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> listView) {
                    return new ImageTextCell();
                }
            });


            friendObservableList = FXCollections.observableArrayList();
            friendObservableList.addAll("Friends", "DIRECT MESSAGES");
            friendObservableList.addAll(getDirectMessages());



            friendView.setItems(friendObservableList);

            chatListView.setItems(null);
            chatHBox.setVisible(false);
            targetFriendHBox.setVisible(false);
            return;
        }


        imageSaver(newValue, "friends", getImageById(newValue, "friends"));

        targetFriendHBox.setVisible(true);
        pinMessages.setVisible(false);
        friendName.setText(fieldId);


        chatField.setDisable(false);
        chatHBox.setVisible(true);
        cancel.setVisible(false);

        chatObservableList = getChatMessages(newValue);

        chatListView.setItems(chatObservableList);

        chatField.setPromptText("Message " + newValue);

        chatListView.setItems(getChatMessages(newValue));

        chatHBox.setVisible(true);
    }


    private void serverHandler(ServerIDs newValue){

        targetFriendHBox.setVisible(false);
        chatListView.setItems(null);
        chatField.setDisable(true);
        chatHBox.setVisible(false);
        cancel.setVisible(true);



        fieldId = null;

        serverId = newValue.getId();

        if(newValue.getId().equals("Add server")){
            newStageMaker("creatServer");

        }
        else if(newValue.getId().equals("Home")){

            serverStatusView.setItems(null);

            friendView.getSelectionModel().selectedItemProperty().removeListener(currentListener);

            currentListener = (obs, oldValue, newVal) -> friendHandler(newVal);

            friendView.getSelectionModel().selectedItemProperty().addListener(currentListener);

            friendHandler("DIRECT MESSAGES");
        }
        else{
            friendView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> listView) {
                    return new ChannelCell(serverId);
                }
            });





            serverStatusView.setItems(getServerMembers(serverId));


            serverStatusView.setCellFactory(new Callback<ListView<Map.Entry<String, UserStatus>>, ListCell<Map.Entry<String, UserStatus>>>() {
                @Override
                public ListCell<Map.Entry<String, UserStatus>> call(ListView<Map.Entry<String, UserStatus>> entryListView) {
                    return new SMemberStatusCell();
                }
            });

            serverStatusView.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldValue, newVal) -> serverMemberHandler(newVal));


            ObservableList<String> channelObs = FXCollections.observableArrayList();
            channelObs.addAll(serverId, "TEXT CHANNELS");
            channelObs.addAll(newValue.getChannels());
            friendView.setItems(channelObs);

            friendView.getSelectionModel().selectedItemProperty().removeListener(currentListener);

            currentListener = (obs, OValue, NValue) -> channelHandler(NValue);

            friendView.getSelectionModel().selectedItemProperty().addListener(currentListener);




        }
    }

    public ObservableList<Map.Entry<String, UserStatus>> getServerMembers(String serverId){
        clientSocket.send(new GetUsersStatusReq(clientSocket.getId(), serverId));

        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted() && response instanceof GetUserStatusRes){

                ArrayList<Map.Entry<String, UserStatus>> res = new ArrayList<>(((GetUserStatusRes) response).getUsers().entrySet());

                return FXCollections.observableArrayList(res);
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }
    public void serverMemberHandler(Map.Entry<String, UserStatus> newValue){

        System.out.println("statusHandler method");

        SUserProController controller = newStageMaker("sUserPro").getController();

        controller.initialize(newValue.getKey(), serverId);
    }

    public void channelHandler(String NValue){

        if(NValue.equals(serverId)){



            targetFriendHBox.setVisible(false);
            chatListView.setItems(null);
            chatField.setDisable(true);
            chatHBox.setVisible(false);
            cancel.setVisible(true);


            ServerRClickController controller = newStageMaker("serverRClick").getController();

            controller.initialize(serverId);
            return;
        }
        else if(NValue.equals("TEXT CHANNELS")){

            targetFriendHBox.setVisible(false);
            chatListView.setItems(null);
            chatField.setDisable(true);
            chatHBox.setVisible(false);
            cancel.setVisible(true);

            CreatChannelController controller = newStageMaker("creatChannel").getController();

            controller.setServerId(serverId);
            return;
        }

        friendName.setText(NValue);
        targetFriendHBox.setVisible(true);
        pinMessages.setVisible(true);


        fieldId = NValue;

        chatField.setDisable(false);
        chatHBox.setVisible(true);
        cancel.setVisible(false);



        chatObservableList = getChannelMessages(NValue);

        chatListView.setItems(chatObservableList);



    }


    private ObservableList<Message> getChannelMessages(String NValue){

        clientSocket.send(new GetChatHistoryReq(clientSocket.getId(), serverId, NValue));
        try{

            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted() && response instanceof GetChatHistoryRes){
                LinkedList<Message> messages = ((GetChatHistoryRes)response).getMessages();

                return FXCollections.observableArrayList(new ArrayList<>(messages));

            }

        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        return null;
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
            if(chatObservableList == null){
                chatObservableList = FXCollections.observableArrayList();
            }

            chatObservableList.add(message);
            chatListView.setItems(chatObservableList);
        }

        chatListView.getSelectionModel().selectedItemProperty()
                .addListener((list, oldValue, newValue) -> chatHandler(newValue));
    }



    public void chatHandler(Message newValue){

        FXMLLoader loader;

        if(serverId != null){
            loader = new FXMLLoader(getClass().getResource("/fxml/Modify.fxml"));
        }
        else{
            clientSocket.send(new GetRulesServerReq(clientSocket.getId(), serverId));

            try{
                Response response = clientSocket.getReceiver().getResponse();

                if(response instanceof GetRulesServerRes && response.isAccepted()){
                    HashMap<String, Rule> roles = ((GetRulesServerRes) response).getRules();

                    if(roles.get(clientSocket.getId()).getRules().contains(RuleType.PIN_MESSAGE)){
                        loader = new FXMLLoader(getClass().getResource("/fxml/ChannelModify.fxml"));
                    }
                    else{
                        loader = new FXMLLoader(getClass().getResource("/fxml/Modify.fxml"));
                    }
                }
                else{
                    return;
                }
            }
            catch(ResponseNotFoundException e){
                e.printStackTrace();
                return;
            }
        }

        try{

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

    public void addItemFriendView(String id) {
        friendObservableList.add(id);

        friendView.setItems(friendObservableList);

    }



    public FXMLLoader newStageMaker(String fxmlFile){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile + ".fxml"));

        try {

            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setOnHidden(e -> hideHandler());
            stage.setMinWidth(600);
            stage.setMinHeight(400);

            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);

            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }


        return loader;
    }

    private void hideHandler()
    {
        initialize();
    }

    @FXML
    void onProfile(ActionEvent event) {
        newStageMaker("myProfile");
    }

    @FXML
    void onSearch(ActionEvent event) {
        changeView("searchFriend", event);
    }

    public String getServerId(){
        return serverId;
    }

    public String getChannelName(){
        return fieldId;
    }

    @FXML
    void onPinMessages(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PinMessages.fxml"));

        try {

            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setOnHidden(e -> hideHandler());
            stage.setMinWidth(600);
            stage.setMinHeight(400);

            stage.focusedProperty().addListener((obs, oldFocus, newFocus) -> focusHandler(newFocus, stage));


            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);

            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


}
