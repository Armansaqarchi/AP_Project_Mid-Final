package client.controller.fxController;

import client.controller.fxController.cell.ChatCell;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.exception.ResponseNotFoundException;
import model.message.Message;
import model.request.Channel.GetPinnedMsgReq;
import model.response.Response;
import model.response.channel.GetPinnedMsgRes;

import java.io.IOException;
import java.util.LinkedList;

public class pinMessagesController extends Controller {

    @FXML
    private ListView<Message> ReactionsView;


    public void initialize(){

        ReactionsView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new ChatCell();
            }
        });

        ReactionsView.setItems(FXCollections.observableArrayList(getPinMessages()));
    }


    public LinkedList<Message> getPinMessages(){

        String serverId = ((HomeController)clientSocket.getReceiver().
                getLoader().getController()).getServerId();

        String channelName = ((HomeController)clientSocket.getReceiver().
                getLoader().getController()).getChannelName();


        clientSocket.send(new GetPinnedMsgReq(clientSocket.getId(), serverId,  channelName));

        try{
            Response response = clientSocket.getReceiver().getResponse();

            if(response.isAccepted() && response instanceof GetPinnedMsgRes){
                return ((GetPinnedMsgRes) response).getPinnedMessages();
            }
        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }
}
