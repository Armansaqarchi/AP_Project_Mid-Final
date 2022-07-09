package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.message.Message;

public class ModifyController extends Controller {

    private Message message;

    @FXML
    private Button Reactions;

    @FXML
    private Button dislike;

    @FXML
    private Button laugh;

    @FXML
    private Button like;


    @FXML
    void onDislike(ActionEvent event) {
        //add dislike to the reactions
    }

    @FXML
    void onLike(ActionEvent event) {
        //add like to reactions
    }

    @FXML
    void onReactions(ActionEvent event) {
        //show list of all the reactions
    }

    @FXML
    public void onLaugh(ActionEvent event) {
        //add laugh to the reactions
    }

    public void setMessage(Message message){
        this.message = message;
    }
}
