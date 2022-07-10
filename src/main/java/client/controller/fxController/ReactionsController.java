package client.controller.fxController;

import client.controller.fxController.cell.ImageTextCell;
import client.controller.fxController.cell.ReactionsCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.message.Message;
import model.message.MessageReaction;
import model.message.Reaction;

import java.util.LinkedList;

public class ReactionsController extends Controller {




    public void initialize(){
        reactions = FXCollections.observableArrayList();


        ReactionsView.setCellFactory(new Callback<ListView<MessageReaction>, ListCell<MessageReaction>>() {
            @Override
            public ListCell<MessageReaction> call(ListView<MessageReaction> messageReactionListView) {
                return new ReactionsCell();
            }
        });
    }

    private ObservableList<MessageReaction> reactions;

    @FXML
    private ListView<MessageReaction> ReactionsView;


    public void getReactions(Message message){
        reactions.addAll(message.getReactions());
        ReactionsView.setItems(reactions);
    }
}
